package com.kotlin.cursos.sqliteexample

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mAdapter : MyDBAdapter? = null
    private var misFacultades = arrayOf("Ingeniería","Negocios","Letras")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarVistas()
        inicializarBaseDatos()
        loadlist()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun inicializarVistas() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        spinner.adapter = ArrayAdapter(this@MainActivity,
                android.R.layout.simple_list_item_1,misFacultades)
        btnAgregar.setOnClickListener {
            mAdapter!!.insertarPersona(txtNombre.text.toString(),
                    spinner.selectedItemPosition + 1)
            loadlist()
        }

        btnEliminar.setOnClickListener {
            mAdapter!!.eliminarPersona()
            loadlist()
        }
    }

    private fun inicializarBaseDatos(){
        mAdapter = MyDBAdapter(this@MainActivity)
        mAdapter?.open()
    }

    private fun loadlist(){
        val todo : List<String>? = mAdapter!!.obtenerPersonas()
        val adapter = ArrayAdapter(this@MainActivity,
                android.R.layout.simple_list_item_1, todo)
        lstPersona.adapter = adapter
    }
}
