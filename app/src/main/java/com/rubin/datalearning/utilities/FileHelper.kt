package com.rubin.datalearning.utilities

import android.content.Context

class FileHelper {
    companion object {
        fun getTextFromResources(context: Context, resourceId: Int): String {
            return context.resources.openRawResource(resourceId).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }

        fun getTextFromAssets(context: Context, filename: String): String {
            return context.assets.open(filename).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }
    }
}