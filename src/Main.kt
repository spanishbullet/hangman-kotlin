import kotlin.random.Random

// Define the HangmanGame class
class HangmanGame {
    private lateinit var word: String
    private lateinit var guessedLetters: MutableSet<Char>
    private var attempts = 6
    private var scores = arrayOf(0, 0) // Scores for Player 1 and Player 2

    // Function to play the game
    fun play() {
        var playAgain = "y"
        var currentPlayer = 0 // Start with Player 1
        // Loop to play again if the user wants to
        while (playAgain == "y") {
            // Initialize the game
            initializeGame(currentPlayer)
            // Loop until the user runs out of attempts
            while (attempts > 0) {
                // Print the current state of the word
                printWord()
                println(" Attempts left: $attempts")
                print("Player ${currentPlayer + 1}, guess a letter: ")
                // Read the user's guess
                val guess = try {
                    readLine()?.firstOrNull()
                } catch (e: Exception) {
                    println("Error reading input: ${e.message}")
                    null
                }

                // Check the user's guess
                when {
                    // If the guess is not a letter, ask for a valid letter
                    guess == null || !guess.isLetter() -> {
                        println("Please enter a valid letter.")
                    }
                    // If the letter has already been guessed, inform the user
                    !guessedLetters.add(guess) -> {
                        println("You already guessed that letter.")
                    }
                    // If the guess is not in the word, decrement the number of attempts
                    guess !in word -> {
                        attempts--
                    }
                    // If all letters in the word have been guessed, the user wins
                    word.all { it in guessedLetters } -> {
                        println("Congratulations, Player ${currentPlayer + 1}, you won! The word was $word.")
                        scores[currentPlayer]++ // Increment the score of the current player
                        break
                    }
                }
            }

            // If the user has no attempts left, they lose
            if (attempts == 0) {
                println("Sorry, Player ${currentPlayer + 1}, you lost. The word was $word.")
            }

            // Ask the user if they want to play again
            print("Do you want to play again? (y/n): ")
            playAgain = try {
                readLine()?.toLowerCase() ?: "n"
            } catch (e: Exception) {
                println("Error reading input: ${e.message}")
                "n"
            }

            // If the game is over, announce the winner
            if (playAgain != "y") {
                val winner = if (scores[0] > scores[1]) 1 else 2
                val loser = if (scores[0] > scores[1]) 2 else 1
                println("Player $winner won the game  ${scores[winner - 1]} to ${scores[loser - 1]}!")
            }

            // Switch to the other player for the next round
            currentPlayer = 1 - currentPlayer
        }
    }

    // Function to initialize the game
    private fun initializeGame(player: Int) {
        // Ask the current player for a word for the other player to guess
        print("Player ${player + 1}, please enter a word for Player ${2 - player} to guess: ")
        word = try {
            readLine() ?: ""
        } catch (e: Exception) {
            println("Error reading input: ${e.message}")
            ""
        }
        // Initialize the set of guessed letters and the number of attempts
        guessedLetters = mutableSetOf()
        attempts = 6
    }

    // Function to print the current state of the word
    private fun printWord() {
        println(word.map { if (it in guessedLetters) it else '_' }.joinToString(" "))
    }
}

// Main function to start the game
fun main() {
    val game = HangmanGame()
    game.play()
}
