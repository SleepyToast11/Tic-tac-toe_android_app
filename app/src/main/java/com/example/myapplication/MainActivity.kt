package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){


    private lateinit var sharedPref: SharedPreferences

    private lateinit var grid: Array<Array<Button>>
    private val model: MyViewMode by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)

        grid = arrayOf(
            arrayOf(findViewById(R.id.tl), findViewById(R.id.tm), findViewById(R.id.tr)),
            arrayOf(findViewById(R.id.ml), findViewById(R.id.mm), findViewById(R.id.mr)),
            arrayOf(findViewById(R.id.bl), findViewById(R.id.bm), findViewById(R.id.br)))

        findViewById<Button>(R.id.reset_button).setOnClickListener {
            reset()
        }

        for(i in grid.indices)
            for(j in grid[i].indices){
                    grid[i][j].text = model.gridState[i][j]

                grid[i][j].setOnClickListener {
                    if (model.buttonUsed(i, j) || model.gameStatus > 0)
                        return@setOnClickListener

                    grid[i][j].text = model.turn
                    model.gridState[i][j] = model.turn

                    updateGameStatus()
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu ->{
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.reset_button ->{
                reset()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateGameStatus(){
        if (model.checkPlayerWon()){
            model.gameStatus = 2
            Toast.makeText(this, model.turn + " won!", Toast.LENGTH_SHORT).show()
            if(sharedPref.getBoolean(getString(R.string.AR), true))
                reset()
            changeTurn()
        }
        else if (model.checkTie()){
            Toast.makeText(applicationContext, "Tie!", Toast.LENGTH_SHORT).show()
            model.gameStatus = 1
            if(sharedPref.getBoolean(getString(R.string.AR), true))
                reset()
        }
        else
            changeTurn()  //turns will change if either won or normal turn change
    }

    fun changeTurn(){
        model.turn =
            if (model.turn == "X")
                "O"
            else
                "X"
    }

    private fun reset(){
        model.reset()
        for (i in grid.indices)
            for (j in grid[i].indices) {
                grid[i][j].text = model.gridState[i][j]
            }
    }
}