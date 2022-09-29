package com.example.myapplication

import android.content.Context
import android.text.BoringLayout
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.selects.SelectInstance

class MyViewMode: ViewModel() {

    var turn = "X"

    var gameStatus = 0

    val gridState : Array<Array<String>> = arrayOf(
        arrayOf("","",""),
        arrayOf("","",""),
        arrayOf("","","")
    )

    private fun collum(int: Int): Array<String>{
        return arrayOf(gridState[0][int], gridState[1][int], gridState[2][int])
    }

    fun reset(){
        for (i in gridState.indices)
            for (j in gridState.indices)
                gridState[i][j] = ""
        gameStatus = 0
    }

    fun buttonUsed(row: Int, col: Int): Boolean{
        return (gridState[row][col] == "X" || gridState[row][col] == "O")
    }

    fun checkTie(): Boolean{
        for (i in gridState.indices)
            for (j in gridState.indices)
                if (gridState[i][j] != "X" && gridState[i][j] != "O")
                    return false
        gameStatus = 1
        return true

    }

    fun checkPlayerWon(): Boolean {
        for (i in 0..2){
            if ((gridState[i].toSet().size == 1) && ((gridState[i][0] == "X") || (gridState[i][0] == "O")))
                    return true
            if ((collum(i).toSet().size == 1) && ((gridState[0][i] == "X") || (gridState[0][i] == "O")))
                return true
        }
        if (gridState[0][0] == gridState[1][1] && gridState[1][1] == gridState[2][2] &&
            ((gridState[0][0] == "X") || (gridState[0][0] == "O")))
            return true

        if (gridState[0][2] == gridState[1][1] && gridState[1][1] == gridState[2][0] &&
            ((gridState[0][2] == "X") || (gridState[0][2] == "O")))
            return true

        return false
    }

}