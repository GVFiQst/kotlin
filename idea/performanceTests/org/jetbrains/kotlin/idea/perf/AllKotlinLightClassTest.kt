/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.perf

import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.cfg.pseudocode.containingDeclarationForPseudocode
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtVisitorVoid
import java.io.File
import kotlin.system.measureNanoTime

class AllKotlinLightClassTest : AllKotlinTest() {
    override fun doTest(file: File): AllKotlinTest.PerFileTestResult {
        val results = mutableMapOf<String, Long>()
        var totalNs = 0L

        val psiFile = file.toPsiFile() ?: run {
            return PerFileTestResult(results, totalNs, listOf(AssertionError("PsiFile not found for $file")))
        }

        val errors = mutableListOf<Throwable>()

        val result = measureNanoTime {
            try {
                // Build light class by PsiFile
                psiFile.acceptRecursively(object : KtVisitorVoid() {
                    override fun visitClassOrObject(classOrObject: KtClassOrObject) {
                        if (classOrObject.containingDeclarationForPseudocode != null) return
                        val lightClass = classOrObject.toLightClass() ?: return
                        lightClass.hashCode()
                    }
                })

            } catch (t: Throwable) {
                t.printStackTrace()
                errors += t
            }
        }
        results["LightClasses_Top"] = result
        totalNs += result

        return PerFileTestResult(results, totalNs, errors)
    }
}