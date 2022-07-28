package com.example.json_lab18

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.json_lab18.data.Contato
import com.example.json_lab18.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var jsonArray: JSONArray? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listaContato = gerarContatos(5)
        binding.button.setOnClickListener {
            jsonArray = gerarJSONArray(listaContato)
            binding.textView2.text = jsonArray.toString()
        }

        binding.button2.setOnClickListener {
            val lista = recuperarListaContato()
            var stringExibicao = ""
            for (i in lista.indices) {
                stringExibicao = "${stringExibicao}\n${lista[i].toString()}"
            }
            binding.textView2.text = stringExibicao
        }

    }

    fun gerarContatos(quantidade: Int): MutableList<Contato> {
        val lista = mutableListOf<Contato>()
        for (i in 1..quantidade) {
            var contatoGerado = Contato(
                idContato = i.toLong(),
                nome = "Contato ${i}",
                idade = i * 2
            )
            lista.add(contatoGerado)
        }
        return lista
    }

    fun gerarJSONArray(lista: MutableList<Contato>): JSONArray {
        val jsonArray = JSONArray()
        lista.forEach {
            jsonArray.put(it.gerarJSON())
        }
        return jsonArray
    }

    fun recuperarContatos(json: JSONObject): Contato {
        val idContato = json.get("idContato").toString().toLong()
        val nome = json.get("nome").toString()
        val idade = json.get("idade").toString().toInt()
        return Contato(idContato, nome, idade)
    }

    fun recuperarListaContato(): MutableList<Contato> {
        val lista = mutableListOf<Contato>()

        jsonArray?.let { array ->
            for (i in 0 until array.length()) {
                lista.add(recuperarContatos(array[i] as JSONObject))
            }

        }
        return lista
    }

}