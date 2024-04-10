import kotlin.random.Random

class HangmanGame(private val words: List<String>) {
    private lateinit var word: String
    private lateinit var guessedLetters: MutableSet<Char>
    private var attempts = 6

    fun play() {
        while (true) {
            initializeGame()
            while (attempts > 0) {
                printWord()
                println(" Attempts left: $attempts")
                print("Guess a letter: ")
                val guess = readLine()?.firstOrNull()

                when {
                    guess == null || !guess.isLetter() -> {
                        println("Please enter a valid letter.")
                    }
                    !guessedLetters.add(guess) -> {
                        println("You already guessed that letter.")
                    }
                    guess !in word -> {
                        attempts--
                    }
                    word.all { it in guessedLetters } -> {
                        println("Congratulations, you won! The word was $word.")
                        break
                    }
                }
            }

            if (attempts == 0) {
                println("Sorry, you lost. The word was $word.")
            }
            println("Starting a new game...")
        }
    }

    private fun initializeGame() {
        word = words[Random.nextInt(words.size)]
        guessedLetters = mutableSetOf()
        attempts = 6
    }

    private fun printWord() {
        println(word.map { if (it in guessedLetters) it else '_' }.joinToString(" "))
    }
}

fun main() {
    val words = listOf("hangman", "kotlin", "programming", "game", "random", "word", "list", "player", "win", "lose", "start", "over", "guess", "letter", "attempt", "initialize", "print", "function", "class", "main")
    val game = HangmanGame(words)
    game.play()
}
