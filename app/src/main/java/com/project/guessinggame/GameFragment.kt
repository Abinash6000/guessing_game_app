package com.project.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.project.guessinggame.databinding.FragmentGameBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner //Each time a live data property value in the view model gets updated, the layout no longer has to rely on the fragment code updating its views. The layout can respond to any updates without any further intervention from the fragment.

//        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
//        })
//
//        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.lives.text = "You have $newValue lives left"
//        })
//
//        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.word.text = newValue
//        })

        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })

        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}