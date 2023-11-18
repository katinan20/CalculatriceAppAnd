package ci.miage.mmm.calculatricekt02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var previewtext: TextView
    lateinit var zonetext: TextView
    lateinit var b0: Button
    lateinit var b1: Button
    lateinit var b2: Button
    lateinit var b3: Button
    lateinit var b4: Button
    lateinit var b5: Button
    lateinit var b6: Button
    lateinit var b7: Button
    lateinit var b8: Button
    lateinit var b9: Button
    lateinit var b10: Button
    lateinit var b11: Button
    lateinit var b12: Button
    lateinit var b13: Button
    lateinit var b14: Button

    lateinit var bvirgul: Button
    lateinit var bmuted: Button
    lateinit var bplus: Button
    lateinit var begale: Button
    lateinit var bmoin: Button
    lateinit var bdiv: Button
    lateinit var bmodul: Button
    lateinit var bc: Button
    lateinit var bmult: Button
    lateinit var bac:  Button

    private var dernierOp: Char? = null
    var str: String =""
    private val listOP= listOf("+","-","*","÷","%")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialization des données

        previewtext = findViewById(R.id.previewtext)
        zonetext    = findViewById(R.id.zonetext)

        b0 = findViewById(R.id.b0)
        b1    = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3    = findViewById(R.id.b3)
        b4 = findViewById(R.id.b4)
        b5    = findViewById(R.id.b5)
        b6 = findViewById(R.id.b6)
        b7    = findViewById(R.id.b7)
        b8 = findViewById(R.id.b8)
        b9    = findViewById(R.id.b9)

        bvirgul = findViewById(R.id.bvirgul)
        bmuted    = findViewById(R.id.bmuted)
        bplus = findViewById(R.id.bplus)
        begale    = findViewById(R.id.begale)
        bmoin = findViewById(R.id.bmoin)
        bdiv    = findViewById(R.id.bdiv)
        bc = findViewById(R.id.bc)
        bmodul    = findViewById(R.id.bmodul)
        bmult = findViewById(R.id.bmult)
        bac   = findViewById(R.id.bac)


        b0.setOnClickListener {

            zonetext.text = (zonetext.text.toString()+"0")
        }

        b1.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"1")
        }

        b2.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"2")
        }

        b3.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"3")
        }

        b4.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"4")
        }

        b5.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"5")
        }

        b6.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"6")
        }

        b7.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"7")
        }

        b8.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"8")
        }

        b9.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"9")
        }



        bvirgul.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+".")
        }

        bmuted.setOnClickListener{
            zonetext.text = (zonetext.text.toString()+"-")
        }

        bplus.setOnClickListener{

            if (dernierOp.toString() in listOP ){
                str = zonetext.text.toString()
                evaluate(str)
            }else {
                zonetext.text = (zonetext.text.toString() + "+")
                dernierOp = '+'
            }

        }

        bmoin.setOnClickListener{

            if (dernierOp.toString() in listOP ){
                str = zonetext.text.toString()
                evaluate(str)
            }else {
                val str: String = zonetext.text.toString()
                if (!str.get(index = str.length - 1).equals("-")) {
                    zonetext.text = (zonetext.text.toString() + "-")
                    dernierOp = '-'

                }

            }
        }

        bmult.setOnClickListener{

            if (dernierOp.toString() in listOP){
                str = zonetext.text.toString()
                evaluate(str)
            }else {
                val str: String = zonetext.text.toString()
                if (!str.get(index = str.length - 1).equals("*")) {
                    zonetext.text = (zonetext.text.toString() + "*")
                    dernierOp = '*'

                }
            }
        }

        bdiv.setOnClickListener{
            if (dernierOp.toString() in listOP ){
                str = zonetext.text.toString()
                evaluate(str)
            }else{
                zonetext.text = (zonetext.text.toString()+"÷")
                dernierOp = '÷'

            }

        }

        begale.setOnClickListener{
            str = zonetext.text.toString()
            val result: Int = evaluate(str)
            val r = result.toString()
            zonetext.setText(r)
        }
        bmodul.setOnClickListener {
            if (dernierOp.toString() in listOP) {
                str = zonetext.text.toString()
                evaluate(str)
            } else {
                zonetext.text = (zonetext.text.toString() + "%")
            }
        }

        bac.setOnClickListener{
            zonetext.setText("")
            previewtext.setText("")
        }
        bc.setOnClickListener{
            var str: String = zonetext.text.toString()
            if (!str.equals("")) {
                str = str.substring(0, str.length - 1)
                zonetext.text = str
            }
        }

    }

    fun evaluate(str: String): Int {
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Int {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x.toInt()
            }

            fun parseExpression(): Int {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) x += parseTerm() // addition
                    else if (eat('-'.toInt())) x -= parseTerm() // subtraction
                    else return x.toInt()
                }
            }

            fun parseTerm(): Int {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.code)) x *= parseFactor() // multiplication
                    else if (eat('/'.toInt())) x /= parseFactor() // division
                    else return x.toInt()
                }
            }


            fun parseFactor(): Int {

                if (eat('+'.code)) return parseFactor() // unary plus
                if (eat('-'.code)) return -parseFactor() // unary minus
                var x: Int
                val startPos = pos
                if (eat('('.code)) { // parentheses
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.toInt()) {
                    // numbers
                    while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                    x = str.substring(startPos, pos).toInt()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) {
                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                    val func = str.substring(startPos, pos)

                    x = parseFactor()

                } else {

                    throw RuntimeException("Unexpected: " + ch.toChar())
                }

                return x
            }

        }.parse()
    }

}