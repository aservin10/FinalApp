package utng.aservin.finalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    //Objects
    private var etxTema: EditText? = null
    private var spiCarrera: Spinner? = null
    private var spiMateria: Spinner? = null
    private var btnReg: Button? = null

    //Database reference
    private var refDiario: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refDiario = FirebaseDatabase.getInstance().getReference("Clase")

        //Objects-View association
        etxTema = findViewById(R.id.etxTema) as EditText
        spiCarrera = findViewById(R.id.spiCarreras) as Spinner
        spiMateria = findViewById(R.id.spiMaterias) as Spinner
        btnReg = findViewById(R.id.btnRegistrar) as Button
        btnReg!!.setOnClickListener { registrarClase() }
    }
    fun registrarClase() {
        val carrera = spiCarrera!!.selectedItem.toString()
        val materia = spiMateria!!.selectedItem.toString()
        val tema = etxTema!!.text.toString()
        if (!TextUtils.isEmpty(tema)) {
            //Database Key
            val id = refDiario!!.push().getKey()
            val leccion = Clase(id, carrera, materia, tema)
            //Lecciones child
            if (id != null) {
                refDiario!!.child("Lecciones").child(id).setValue(leccion)
            }
            Toast.makeText(
                this, "Clase registrada",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this, "Ingresar Tema",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
