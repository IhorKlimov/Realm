package com.myhexaville.realm

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.myhexaville.realm.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmConfiguration


class MainActivity : AppCompatActivity() {
    private val LOG_TAG = "MainActivity"
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        realm = Realm.getInstance(config)
        basicCRUD()
    }

    override fun onDestroy() {
        realm?.close()
        super.onDestroy()
    }

    private fun basicCRUD() {
        // delete all users
        realm?.executeTransaction { realm ->
            realm.delete(User::class.java)
        }

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm?.executeTransaction { realm ->
            val person = User()
            person.name = "Ihor"
            person.age = 25
//            realm.copyToRealm(person)
        }

        // Find the first person (no query conditions) and read a field
        var person = realm?.where(User::class.java)?.findFirst()
        Log.d(LOG_TAG, person.toString())

        // Update person in a transaction
        realm?.executeTransaction {
            person?.name = "Ihor Klimov"
        }

        person = realm?.where(User::class.java)?.findFirst()
        Log.d(LOG_TAG, person.toString())

        realm?.executeTransaction {
            val frank = User("Frank", 20)
            realm?.copyToRealm(frank)
        }

    }
}