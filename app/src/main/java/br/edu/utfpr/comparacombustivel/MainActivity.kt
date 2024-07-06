package br.edu.utfpr.comparacombustivel

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.comparacombustivel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvResultado.text = ""

        binding.iconButtonBuscar1.setOnClickListener {
            buscarConsumoCombustivel1()
        }

        binding.iconButtonBuscar2.setOnClickListener {
            buscarConsumoCombustivel2()
        }

        binding.btnCalcular.setOnClickListener {
            calcular()
        }

        binding.btnLimpar.setOnClickListener {
            limpar()
        }

    }

    private fun buscarConsumoCombustivel1() {

        var intent = Intent(this, BuscarConsumoCombustivelActivity::class.java)
        getConsumoCombustivel1.launch(intent)
    }

    private fun buscarConsumoCombustivel2() {
        var intent = Intent(this, BuscarConsumoCombustivelActivity::class.java)
        getConsumoCombustivel2.launch(intent)
    }

    private val getConsumoCombustivel1 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {

                    val codigoCombustivel = it.data?.getIntExtra("codCombustivel", 0)
                    val nomeCombustivel = it.data?.getStringExtra("nomeCombustivel")

                    val consumoGasolina = it.data?.getStringExtra("consumo")
                    val consumoEtanol = it.data?.getStringExtra("consumo")
                    val consumoDiesel = it.data?.getStringExtra("consumo")
                    val consumoInvalido = it.data?.getStringExtra("consumo")

                    when (codigoCombustivel) {
                        1 -> binding.etConsumoCombustivel1.setText(consumoGasolina).toString()
                        2 -> binding.etConsumoCombustivel1.setText(consumoEtanol).toString()
                        3 -> binding.etConsumoCombustivel1.setText(consumoDiesel).toString()
                        else -> consumoInvalido
                    }

                    binding.tilConsumoCombustivel1.hint = nomeCombustivel.toString()
                    binding.tilPrecoCombustivel1.hint = nomeCombustivel.toString()


                }
            }
        }

    private val getConsumoCombustivel2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {

                    val codigoCombustivel = it.data?.getIntExtra("codCombustivel", 0)
                    val nomeCombustivel = it.data?.getStringExtra("nomeCombustivel")

                    val consumoGasolina = it.data?.getStringExtra("consumo")
                    val consumoEtanol = it.data?.getStringExtra("consumo")
                    val consumoDiesel = it.data?.getStringExtra("consumo")
                    val consumoInvalido = it.data?.getStringExtra("consumo")

                    when (codigoCombustivel) {
                        1 -> binding.etConsumoCombustivel2.setText(consumoGasolina).toString()
                        2 -> binding.etConsumoCombustivel2.setText(consumoEtanol).toString()
                        3 -> binding.etConsumoCombustivel2.setText(consumoDiesel).toString()
                        else -> consumoInvalido
                    }

                    binding.tilConsumoCombustivel2.hint = nomeCombustivel.toString()
                    binding.tilPrecoCombustivel2.hint = nomeCombustivel.toString()

                }
            }
        }

    private fun calcular() {

        desabiitaAvisoErro()
        var campoValido = true

        val stringConsumo1 = binding.etConsumoCombustivel1.text.toString()

        val stringPreco2 = binding.etPrecoCombustivel2.text.toString()
        if (stringPreco2.isNullOrEmpty()) {
            binding.tilPrecoCombustivel2.error = getString(R.string.campo_obrigatorio)
            binding.tilPrecoCombustivel2.requestFocus()
            campoValido = false
        }

        val stringPreco1 = binding.etPrecoCombustivel1.text.toString()
        if (stringPreco1.isNullOrEmpty()) {
            binding.tilPrecoCombustivel1.error = getString(R.string.campo_obrigatorio)
            binding.tilPrecoCombustivel1.requestFocus()
            campoValido = false
        }

        val stringConsumo2 = binding.etConsumoCombustivel2.text.toString()
        if (stringConsumo2.isNullOrEmpty()) {
            binding.tilConsumoCombustivel2.error = getString(R.string.campo_obrigatorio)
            binding.tilConsumoCombustivel2.setHint(R.string.combustivel_2)
            binding.tilPrecoCombustivel2.setHint(R.string.combustivel_2)
            binding.tilConsumoCombustivel2.requestFocus()
            campoValido = false
        }

        if (stringConsumo1.isNullOrEmpty()) {
            binding.tilConsumoCombustivel1.error = getString(R.string.campo_obrigatorio)
            binding.tilConsumoCombustivel1.setHint(R.string.combustivel_1)
            binding.tilPrecoCombustivel1.setHint(R.string.combustivel_1)
            binding.tilConsumoCombustivel1.requestFocus()
            campoValido = false
        }

        if (campoValido == false) {
            return
        }

        val consumo1 = stringConsumo1.toDouble()
        val consumo2 = stringConsumo2.toDouble()
        val preco1 = stringPreco1.toDouble()
        val preco2 = stringPreco2.toDouble()

        var resultado1 = preco1 / consumo1
        var resultado2 = preco2 / consumo2

        val dec = DecimalFormat("#.##")

        if (resultado1 > resultado2) {
            binding.tvResultado.text = getString(
                R.string.combustivel_mais_vantajoso,
                binding.tilConsumoCombustivel2.getHint(),
                "\nValor gasto por Km: R$ ${dec.format(resultado2)}",
                "\n\nCombustível comparado: ${binding.tilConsumoCombustivel1.getHint()}",
                "\nValor gasto por Km: R$ ${dec.format(resultado1)}"
            )
            Toast.makeText(this, "Sugestão para abastecimento: ${binding.tilConsumoCombustivel2.getHint()}", Toast.LENGTH_LONG).show()
        } else if (resultado1 < resultado2) {
            binding.tvResultado.text = getString(
                R.string.combustivel_mais_vantajoso,
                binding.tilConsumoCombustivel1.getHint(),
                "\nValor gasto por Km: R$ ${dec.format(resultado1)}",
                "\n\nCombustível comparado: ${binding.tilConsumoCombustivel2.getHint()}",
                "\nValor gasto por Km: R$ ${dec.format(resultado2)}"
            )
            Toast.makeText(this, "Sugestão para abastecimento: ${binding.tilConsumoCombustivel1.getHint()}", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "Sugestão para abastecimento: Tanto faz", Toast.LENGTH_LONG).show()
        }

    }

    private fun limpar() {

        desabiitaAvisoErro()

        binding.tilConsumoCombustivel1.setHint(R.string.combustivel_1)
        binding.tilConsumoCombustivel2.setHint(R.string.combustivel_2)
        binding.tilPrecoCombustivel1.setHint(R.string.combustivel_1)
        binding.tilPrecoCombustivel2.setHint(R.string.combustivel_2)
        binding.etConsumoCombustivel1.setText("")
        binding.etConsumoCombustivel2.setText("")
        binding.etPrecoCombustivel1.setText("")
        binding.etPrecoCombustivel2.setText("")
        binding.tvResultado.text = getString(R.string.combustivel_mais_vantajoso, "", "", "", "")
        binding.tilConsumoCombustivel1.requestFocus()

    }

    private fun desabiitaAvisoErro() {

        binding.tilConsumoCombustivel1.isErrorEnabled = false
        binding.tilConsumoCombustivel2.isErrorEnabled = false
        binding.tilPrecoCombustivel1.isErrorEnabled = false
        binding.tilPrecoCombustivel2.isErrorEnabled = false
    }

}