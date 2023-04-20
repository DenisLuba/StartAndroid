package com.example.p1151_multiplescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.p1151_multiplescreen.databinding.DetailsBinding

class DetailsFragment : Fragment() {

    private val binding: DetailsBinding by lazy {
        DetailsBinding.inflate(layoutInflater)
    }

    //    arguments (getArguments()/setArguments() in Java) возвращает аргументы (атрибуты), переданные при создании фрагмента
//    (строго до того, как фрагмент будет присоединен к какому-либо активити)
//    , если они имеются. Они хранятся в фрагменте даже после того, как он был пересоздан
//    в результате, например, смены ориентации экрана.
    fun position() = arguments?.getInt("position") ?: 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root.apply {
        binding.tvText.text =
            resources.
            getStringArray(R.array.content)[ position() ]
    }

//    получаем инстанс этого фрагмента
//    (Cистема будет создавать фрагмент вовсе не через метод getInstance.
//    Она просто не знает такой метод. Система использует конструктор.
//    Система сохраняет аргументы фрагмента при его пересоздании.),
//    добавляем ему аргумент (атрибут) и возвращаем этот экземпляр
    companion object {
        @JvmStatic fun getInstance(position: Int) = DetailsFragment().apply {
            val bundle = Bundle()
            bundle.putInt("position", position)
            arguments = bundle
        }
    }
}