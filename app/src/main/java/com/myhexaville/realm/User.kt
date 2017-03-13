package com.myhexaville.realm

import io.realm.RealmObject

open class User(
        var name: String? = null,
        var age: Int? = null)
    : RealmObject()