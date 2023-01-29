#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

int main(){
    ifstream file;
    file.open("input.txt");
    vector<int> calories;
    int value;
    int elfCalories = 0;
    int maxCalories = 0;
    string line;

    if(file.is_open()){
        while(getline(file, line)){
            if(line.empty()){
                if(elfCalories > maxCalories){
                    maxCalories = elfCalories;
                }
                calories.push_back(elfCalories);
                elfCalories = 0;
            }
            else {
                elfCalories += stoi(line);
            }
        }
    }
    cout << "The elf carrying the most calories is carrying " << maxCalories << " calories!" << endl;
    
    sort(calories.begin(), calories.end());

    int topThree = calories.back() + calories[calories.size() - 2] + calories[calories.size() - 3];

    cout << "Top three elves are carrying " << topThree << " calories in total!" << endl;
    return 0;
}