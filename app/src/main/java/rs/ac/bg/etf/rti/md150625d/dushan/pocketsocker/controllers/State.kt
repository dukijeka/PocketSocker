package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers


/*
 * Describes phases of a move
 */
enum class State {
    SELECTION, // player selects a football player to play with
    MOVEMENT, // player moves the finger, thus selecting the speed and direction
    //PLAY // player lifted a finger from the screen, the move is played
}