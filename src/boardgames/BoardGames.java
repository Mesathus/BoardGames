/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;
import java.util.Scanner;

/**
 *
 * @author Mesathus
 */
public class BoardGames {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s = new Scanner(System.in);
        TicTacToe newgame = new TicTacToe();
        boolean quit = false;
        while(!quit)
        {
            Print("Beginning new game");
            newgame.PlayGame();
            Print("Would you like to play another game?");
            String input = s.nextLine();
            if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes"))
            {
                quit = false;
                newgame.NewGame();
            }
            else
            {
                quit = true;
            }
        }
    }
    public static void Print(String toPrint)
    {
        System.out.println(toPrint);
    }
}
