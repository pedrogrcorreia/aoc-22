#include <iostream>
#include <fstream>
#include <string>

enum Result {
    WIN = 6,
    DRAW = 3,
    LOSE = 0,
};

enum Moves {
    ROCK = 1,
    PAPER = 2,
    SCISSOR = 3,
};

Moves playValue(char play);

Result getResult(char result);

Result gameResult(Moves elfMove, Moves myMove);

Moves neededMove(Moves elfMove, Result result);

int main(){
    std::ifstream file;
    file.open("input.txt");

    if(!file.is_open()){
        std::cout << "Error opening file!" << std::endl;
    }

    std::string line;

    int points = 0;

    int secPoints = 0;

    int roundPoints = 0;

    while(getline(file, line)){
        roundPoints = playValue(line[2]);
        roundPoints += gameResult(playValue(line[0]), playValue(line[2]));
        points += roundPoints;

        secPoints += neededMove(playValue(line[0]), getResult(line[2]));
        secPoints += getResult(line[2]);
    }

    std::cout << "Total points: " << points << std::endl;

    std::cout << "Total points: " << secPoints << std::endl;
    return 0;
}

Moves playValue(char play){
    switch(play){
        case 'A':
        case 'X': return ROCK;
        case 'B':
        case 'Y': return PAPER;
        case 'C':
        case 'Z': return SCISSOR;
    }
}

Result getResult(char result){
    switch(result){
        case 'X': return LOSE;
        case 'Y': return DRAW;
        case 'Z': return WIN;
    }
}

Result gameResult(Moves elfMove, Moves myMove){
    if(elfMove == myMove){
        return DRAW;
    }
    if(elfMove == ROCK && myMove == SCISSOR ||
        elfMove == PAPER && myMove == ROCK ||
        elfMove  == SCISSOR && myMove == PAPER){
            return LOSE;
        }
    return WIN;
}

Moves neededMove(Moves elfMove, Result result){
    if(result == DRAW){
        return elfMove;
    }
    if(result == LOSE){
        switch(elfMove){
            case ROCK: return SCISSOR;
            case PAPER: return ROCK;
            case SCISSOR: return PAPER;
        }
    }
    if(result == WIN){
        switch(elfMove){
            case ROCK: return PAPER;
            case PAPER: return SCISSOR;
            case SCISSOR: return ROCK;
        }
    }
}