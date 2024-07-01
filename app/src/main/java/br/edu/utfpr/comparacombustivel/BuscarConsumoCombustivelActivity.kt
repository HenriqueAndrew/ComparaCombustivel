package br.edu.utfpr.comparacombustivel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.comparacombustivel.databinding.ActivityBuscarConsumoCombustivelBinding

class BuscarConsumoCombustivelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarConsumoCombustivelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarConsumoCombustivelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lvCombustivel.setOnItemClickListener { parent, view, position, id ->

            val codCombustivel = position + 1
            val nomeCombustivel = parent.getItemAtPosition(position).toString()

            val consumoGasolina = getString(R.string.consumo_gasolina)
            val consumoEtanol = getString(R.string.consumo_etanol)
            val consumoDiesel = getString(R.string.consumo_diesel)
            val consumoInvalido = getString(R.string.combustivel_invalido)

            intent.putExtra("codCombustivel", codCombustivel)
            intent.putExtra("nomeCombustivel", nomeCombustivel)

            when (codCombustivel) {
                1 -> intent.putExtra("consumo", consumoGasolina)
                2 -> intent.putExtra("consumo", consumoEtanol)
                3 -> intent.putExtra("consumo", consumoDiesel)
                else -> intent.putExtra("consumo", consumoInvalido)
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}