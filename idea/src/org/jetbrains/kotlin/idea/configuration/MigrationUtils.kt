/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.configuration

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.DataContext

internal val dataContext: DataContext?
    get() = DataManager.getInstance().dataContextFromFocus.result