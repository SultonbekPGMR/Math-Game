package uz.codial.mathgame

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat.ThemeCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var answer = 0

    var score = 0
    var steps = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start()

        txt_score.setOnLongClickListener() {
            showDefaultDialog(this)
            true
        }

    }

    fun start() {
        txt_score.text = "$score/$steps"
        random()
        randomVariants()
    }

    fun randomVariants() {
        val numbers = arrayListOf<Int>()
        numbers.add(Random.nextInt(answer - 10, answer + 10))
        numbers.add(Random.nextInt(answer - 10, answer + 10))
        numbers.add(Random.nextInt(answer - 10, answer + 10))

        numbers.sort()
        if (numbers.contains(answer) || numbers[0] == numbers[1] || numbers[1] == numbers[2])
            randomVariants()


        numbers.add(answer)
        val variants = arrayListOf(0, 1, 2, 3)
        variants.shuffle()

        txt_1.text = numbers[variants[variants.size - 1]].toString()
        variants.removeLast()
        txt_2.text = numbers[variants[variants.size - 1]].toString()
        variants.removeLast()
        txt_3.text = numbers[variants[variants.size - 1]].toString()
        variants.removeLast()
        txt_4.text = numbers[variants[variants.size - 1]].toString()
        variants.removeLast()


    }

    private fun random() {
        var num1 = Random.nextInt(40) + 1
        var num2 = Random.nextInt(20) + 1

        var ishora = Random.nextInt(4)

        try {
            when (ishora) {
                0 -> {
                    txt_quiz.text = "$num1 + $num2"
                    answer = num1 + num2
                }
                1 -> {
                    txt_quiz.text = "$num1 - $num2"
                    answer = num1 - num2
                }
                2 -> {
                    if (num1 * num2 > 200) {
                        throw Exception("WRONG random number")
                    } else {
                        txt_quiz.text = "$num1 × $num2"
                        answer = num1 * num2
                    }

                }
                3 -> {
                    if (num1 > num2 || num1 % num2 != 0) {
                        throw Exception("WRONG random number")
                    } else {
                        txt_quiz.text = "$num1 ÷ $num2"
                        answer = num1 / num2
                    }

                }
            }


        } catch (e: Exception) {
            Log.d(TAG, "$e")
            random()
        }


    }

    fun startCheck(view: View) {
        steps++
        when (view.tag.toString().toInt()) {
            1 -> if (txt_1.text.toString().toInt() != answer) {
                fals()
            } else tru()
            2 -> if (txt_2.text.toString().toInt() != answer)
                fals()
            else tru()
            3 -> if (txt_3.text.toString().toInt() != answer)
                fals()
            else tru()
            4 -> if (txt_4.text.toString().toInt() != answer)
                fals()
            else tru()
        }
        txt_score.text = "$score/$steps"


        start()

    }

    fun tru() {
        txt_result.text = "Correct"
        txt_result.setTextColor(Color.parseColor("#00FF00"))
        score++

        txtTimer()

    }

    fun fals() {
        txt_result.text = "Wrong"
        txt_result.setTextColor(Color.parseColor("#FF0000"))
        txtTimer()
    }

    fun refresh(view: View) {
        score = 0
        steps = 0
        txt_result.text = ""
        txt_score.text = "$score/$steps"
        start()


    }

    fun txtTimer() {
        val t = Timer(false)
        t.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { txt_result.text = "" }
            }
        }, 1500)
    }


    private fun showDefaultDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(context,R.style.MyDialogTheme)

        alertDialog.apply {
            //setIcon(R.drawable.ic_hello)
            setTitle("About App")
            setMessage("This app was built by Sultonbek To'lanov.\nIf you face any problem Please feel free to contact me -> Telegram: https://t.me/pgmr1\n26.10.2022")
            setNegativeButton("close") { _, _ ->
            }

        }.create().show()
    }


}