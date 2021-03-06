// IGNORE_BACKEND: JVM_IR
// IGNORE_BACKEND: JS_IR
// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS, NATIVE

// WITH_REFLECT

fun box(): String {

    val objectInLambda = {
        object : Any () {}
    }()

    val enclosingMethod = objectInLambda.javaClass.getEnclosingMethod()
    if (enclosingMethod?.getName() != "invoke") return "method: $enclosingMethod"

    val enclosingClass = objectInLambda.javaClass.getEnclosingClass()!!.getName()
    if (enclosingClass != "ObjectInLambdaKt\$box\$objectInLambda\$1") return "enclosing class: $enclosingClass"

    val declaringClass = objectInLambda.javaClass.getDeclaringClass()
    if (declaringClass != null) return "anonymous object has a declaring class"

    return "OK"
}
