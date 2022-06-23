package com.example.spacefight;

 public class  Difficulty {
     enum Level {
         easy,
         medium,
         hard
     }
     Level difficultyLevel = Level.easy;

     public Difficulty(Level difficulty) {
         this.difficultyLevel = difficulty;
     }

     public int enemySpeed() {
         switch(difficultyLevel) {
             case easy:
                 return 10;
             case medium:
                 return 15;
             case hard:
                 return 20;
             default:
                 return 10;
         }
     }

     public int enemyShotsSpeed() {
         switch(difficultyLevel) {
             case easy:
                 return 10;
             case medium:
                 return 15;
             case hard:
                 return 20;
             default:
                 return 10;
         }
     }

     public int bonusSpeed() {
         switch(difficultyLevel) {
             case easy:
                 return 15;
             case medium:
                 return 20;
             case hard:
                 return 30;
             default:
                 return 15;
         }
     }

     public int enemyFireRate() {
         switch(difficultyLevel) {
             case easy:
                 return 100;
             case medium:
                 return 70;
             case hard:
                 return 40;
             default:
                 return 100;
         }
     }

     public int timerForBonus() {
         switch(difficultyLevel) {
             case easy:
                 return 600;
             case medium:
                 return 1000;
             case hard:
                 return 1500;
             default:
                 return 600;
         }
     }
 }
