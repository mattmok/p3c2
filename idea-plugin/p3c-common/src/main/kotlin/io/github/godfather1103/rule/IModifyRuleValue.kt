package io.github.godfather1103.rule

import io.github.godfather1103.service.BaseNameListServiceExt

interface IModifyRuleValue {

    fun className(): String {
        return javaClass.simpleName
    }

    fun modifyValue(base: BaseNameListServiceExt, key: String)
}