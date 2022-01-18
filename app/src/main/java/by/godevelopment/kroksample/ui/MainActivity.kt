package by.godevelopment.kroksample.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.LANG_BY_KEY
import by.godevelopment.kroksample.common.LANG_ENG_KEY
import by.godevelopment.kroksample.common.LANG_RU_KEY
import by.godevelopment.kroksample.common.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _navController: NavController? = null
    private val navController get() = _navController!!
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.krok_toolbar))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        _navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        setupWithNavController(findViewById(R.id.krok_toolbar), navController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.lang_by -> {
            Log.i(TAG, "onOptionsItemSelected: BY_KEY")
            mainViewModel.setLangPreference(LANG_BY_KEY)
            true
        }
        R.id.lang_eng -> {
            Log.i(TAG, "onOptionsItemSelected: ENG_KEY")
            mainViewModel.setLangPreference(LANG_ENG_KEY)
            true
        }
        R.id.lang_ru -> {
            Log.i(TAG, "onOptionsItemSelected: RU_KEY")
            mainViewModel.setLangPreference(LANG_RU_KEY)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

//        val searchItem = menu?.findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as SearchView
        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        _navController = null
        super.onDestroy()
    }
}