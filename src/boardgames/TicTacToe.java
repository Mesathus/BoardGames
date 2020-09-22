/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;
import java.lang.Math;
import java.util.Scanner;
/**
 *
 * @author Mesathus
 */
public class TicTacToe {
    private int activePlayer;    //variable to track the current player
    private char[][] gameBoard;
    private final static int boardSize = 5; //adjust this to change the size of the board from 3x3
    //constructor
    public TicTacToe()
    {
        activePlayer = 1;
        gameBoard = new char[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++)  //initialize the board array so it can be printed
        {
            for(int j = 0; j < boardSize; j++)
            {
                gameBoard[i][j] = '-';
            }
        }
    }
    public void PlayGame()  //primary class function to begin playing the game
    {
        boolean gameOver = false;
        Scanner s = new Scanner(System.in);
        Print("Indicate board positions like so.");
        Print(SampleBoard());
        while(!gameOver)
        {            
            Print("The board currently looks like this.");
            PrintBoard();
            Print(String.format("Player %1$d please enter a position.", activePlayer));
            boolean valid = false;
            while(!valid)
            {
                String input = s.nextLine();
                if(TryParse(input)) //check user value is an integer
                {
                    int position = Integer.parseInt(input);
                    if(position < 1 || position > (boardSize * boardSize))  //error checking the position is within array bounds
                    {
                        Print(String.format("Please only enter numbers between 1 and %1$d", (boardSize * boardSize)));
                    }
                    else
                    {
                        if(SpotEmpty(position)) //check if the selected position is empty
                        {
                            FillSpot(position); //if spot is empty fill with an X or O
                            valid = true;   //end the validation loop
                        }
                        else
                        {
                            Print(String.format("Position %1$d is filled already, please choose another position.", position));
                        }
                    }
                }
                else
                {
                    Print(String.format("Please only enter numbers between 1 and %1$d", (boardSize * boardSize)));
                }
            }
            if(CheckWinner())
            {
                gameOver = true;
            }
        }
        PrintBoard();
        switch(activePlayer)    //activePlayer is adjusted before the winner is check, so the loser will be the current player
        {
            case 1: Print("Player 2 has won!"); break;
            case 2: Print("Player 1 has won!"); break;
        }
    }
    private String SampleBoard()    //function to print an example board that displays position numbers
    {
        String sample = "";
        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                sample += Integer.toString((i * boardSize) + j + 1) + "\t";
            }
            sample += "\n";
        }
        return sample;
    }
    private boolean CheckWinner()   //function to determine if a player has three in a row
    {
        String winner = ""; //string that will hold a combination of -XO to send to the boolean GetWinner function
        for(int i = 0; i < boardSize; i++)  //loop to check horizontal rows
        {
            winner = "";
            for(int j = 0; j < boardSize; j++)
            {
                winner += gameBoard[i][j];
            }
            if(GetWinner(winner))
            {
                return true;
            }
        }
        for(int j = 0; j < boardSize; j++)  //loop to check vertical columns
        {
            winner = "";
            for(int i = 0; i < boardSize; i++)
            {
                winner += gameBoard[i][j];
            }
            if(GetWinner(winner))
            {
                return true;
            }
        }
        winner = "";
        for(int i = 0; i < boardSize; i++)  //loop to check the diagonal beginning in the top left
        {            
            winner += gameBoard[i][i];
        }
        if(GetWinner(winner))
        {
                return true;
        }
        winner = "";
        int j = 0;
        for(int i = boardSize - 1; i >= 0; --i) //loop to check the diagonal beginning in the bottom left
        {
            winner += gameBoard[i][j];
            j++;
        }
        if(GetWinner(winner))
        {
                return true;
        }
        return false;
    }
    private boolean GetWinner(String winner)    //string checking function to see if all characters match
    {
        if(winner.charAt(0) == 'X' || winner.charAt(0) == 'O')  //validate that the string consists of X or O and isn't empty
        {
            for(int i = 1; i < winner.length(); i++)    //loop through the provided string to check if each character matches
            {
                if(winner.charAt(i-1) != winner.charAt(i))  //if characters don't match player does not have a winning row
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    private boolean SpotEmpty(int position) //function to check if the user is trying to use a position that is filled already
    {
        int i = (int)Math.ceil((double)position/boardSize) - 1, j;  //determine the row of the array they're accessing
        if(position%boardSize == 0) //check which column of the array they're accessing
            j = position%boardSize + boardSize - 1; //if modulus returns 0 we want the last column, so set j to boardSize - 1
        else
            j = position%boardSize - 1; //otherwise we'll get the column we want
        if(gameBoard[i][j] == 'X' || gameBoard[i][j] == 'O')    //check and see if the array position is filled
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private void FillSpot(int position) //function to assign an X or O based on position
    {
        int i = (int)Math.ceil((double)position/boardSize) - 1, j;  //as SpotEmpty function, determine the row of the array
        if(position%boardSize == 0) //checking which column of the array to use
            j = position%boardSize + boardSize - 1;
        else
            j = position%boardSize - 1;
        /*if(activePlayer == 1)
        {
            gameBoard[i][j] = 'X';  //player 1 assigns X values
            activePlayer++; //increment to player 2's turn
        }
        else if(activePlayer == 2)
        {
            gameBoard[i][j] = 'O';  //player 2 assigns O values
            activePlayer--; //decrement to player 1's turn
        }
        else
        {
            Print("Error has occured");
            System.exit(0);
        }*/
        switch(activePlayer)
        {
            case 1: gameBoard[i][j] = 'X'; activePlayer++; break;   //player 1 assigns 'X', then increment to player 2s turn
            case 2: gameBoard[i][j] = 'O'; activePlayer--; break;   //player 2 assigns 'O', then decrement to player 1s turn
            default: Print("Error has occured"); System.exit(0);    //error if activePlayer becomes an unexpected value
        }
    }
    private void PrintBoard()   //function to display the current game board
    {
        for(int i = 0; i < boardSize; i++)
        {
            String board = "";
            for(int j = 0; j < boardSize; j++)
            {
                board += gameBoard[i][j] + "\t";
            }            
            Print(board + "\n");
        }
    }    
    private void Print(String toPrint)  //string print helper function
    {
        System.out.println(toPrint);
    }    
    public void NewGame()   //public function to begin a new game
    {
        activePlayer = 1;
        gameBoard = new char[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                gameBoard[i][j] = '-';
            }
        }        
    }
    private boolean TryParse(String toParse)    //integer parsing helper function
    {
        try
        {
            Integer.parseInt(toParse);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
