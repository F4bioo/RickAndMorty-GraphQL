package com.fappslab.rickandmortygraphql.libraries.arch.args.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.fappslab.rickandmortygraphql.libraries.arch.args.ArgsProperty
import com.fappslab.rickandmortygraphql.libraries.arch.args.KEY_ARGS
import kotlin.properties.ReadOnlyProperty

inline fun <reified A : Activity> Context.createIntent(
    flags: Int? = null,
    params: Intent.() -> Unit = {}
) = Intent(this, A::class.java)
    .apply(params)
    .also { intent -> flags?.let { intent.flags = it } }

fun <P : Parcelable> Activity.viewArgs(): ReadOnlyProperty<Activity, P> = ArgsProperty { activity ->
    activity.intent.extras ?: throw IllegalStateException("Have you invoked putArguments?")
}

fun Intent.putArguments(args: Parcelable): Intent = putExtra(KEY_ARGS, args)
