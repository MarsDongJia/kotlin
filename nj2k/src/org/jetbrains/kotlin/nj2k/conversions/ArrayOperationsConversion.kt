/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.nj2k.conversions

import org.jetbrains.kotlin.nj2k.NewJ2kConverterContext
import org.jetbrains.kotlin.nj2k.tree.*
import org.jetbrains.kotlin.nj2k.tree.impl.JKFieldAccessExpressionImpl


class ArrayOperationsConversion(private val context: NewJ2kConverterContext) : RecursiveApplicableConversionBase() {
    override fun applyToElement(element: JKTreeElement): JKTreeElement {
        if (element !is JKQualifiedExpression) return recurse(element)
        val selector = element.selector as? JKFieldAccessExpression ?: return recurse(element)
        if (element.receiver.type(context.symbolProvider) !is JKJavaArrayType) return recurse(element)
        if (selector.identifier.name == "length") {
            val sizeCall =
                    JKFieldAccessExpressionImpl(
                        context.symbolProvider.provideByFqName("kotlin/Array.size")
                    )
            element.selector = sizeCall
        }
        return recurse(element)
    }
}