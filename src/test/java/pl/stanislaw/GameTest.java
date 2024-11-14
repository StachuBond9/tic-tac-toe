package pl.stanislaw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void playerWin(){
        //given
        Player<Character> player1 = new Player<>("Player1", 'O');
        Player<Character> player2 = new Player<>("Player2", 'X');
        Board<Character> board = new Board<>(' ');
        Game<Character> game =  new Game<>(player1,player2,board);

        //when
        game.makeMove(new int[]{ 2,1}, player1 );
        game.makeMove(new int[]{ 1,1}, player1 );
        game.makeMove(new int[]{ 0,1}, player1 );

        //then
        Assertions.assertTrue(game.playerWin(player1));




    }


}