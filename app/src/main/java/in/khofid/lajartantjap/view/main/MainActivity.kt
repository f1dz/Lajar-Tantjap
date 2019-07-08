package `in`.khofid.lajartantjap.view.main

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.view.favorite.FavoritesFragment
import `in`.khofid.lajartantjap.view.movie.MovieFragment
import `in`.khofid.lajartantjap.view.tv.TvShowFragment
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.movies -> loadFragment(MovieFragment())
                R.id.tvShow -> loadFragment(TvShowFragment())
                R.id.favorites -> loadFragment(FavoritesFragment())
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.movies
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_change_settings){
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, fragment::class.java.simpleName)
            .commit()
    }
}
