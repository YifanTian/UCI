//
//  QuizSessionManager.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

// This class should manage the model for the current quiz being taken
// It should also be able to save and recover its data if the app closes

protocol QuizSessionManagerProtocol
{
    func updateQuestion()
    func updateScore()
    func updateTimer(timeCount: Int, timeLimit: Int)
}

class QuizSessionManager
{
    //let questionTemplates: [String] = ["Who directed movie X", "When was the movie X released", "Which star was in the movie X", "Which star was not in the movie X", "In which movie do the stars X and Y appear", "Who directed the star X", "Who did not direct the star X", ""]
    
    var delegate: QuizSessionManagerProtocol
    
    var currentQuestion: Question?
    
    var score: Int = 0
    var incorrectAnswers: Int = 0
    
    var timer: NSTimer?
    var timeCount: Int = 0
    var timeLimit: Int = 3 * 60
    
    init(delegate: QuizSessionManagerProtocol)
    {
        self.delegate = delegate
        startTimer()
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "pauseSession", name: "APP_PAUSED", object: nil)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "resumeSession", name: "APP_RESUMED", object: nil)
    }
    
    deinit
    {
        NSNotificationCenter.defaultCenter().removeObserver(self, name: "APP_PAUSED", object: nil)
        NSNotificationCenter.defaultCenter().removeObserver(self, name: "APP_RESUMED", object: nil)
    }
    
    @objc func pauseSession()
    {
        let defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()
        
        let encodedQuestion = NSKeyedArchiver.archivedDataWithRootObject(self.currentQuestion!)
        
        defaults.setInteger(score, forKey: "QuizSession_Score")
        defaults.setInteger(incorrectAnswers, forKey: "QuizSession_IncorrectAnswers")
        defaults.setInteger(timeCount, forKey: "QuizSession_TimeCount")
        defaults.setObject(encodedQuestion, forKey: "QuizSession_Question")
        
        stopTimer()
    }
    
    @objc func resumeSession()
    {
        let defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()
        
        let encodedQuestion: NSData = defaults.objectForKey("QuizSession_Question") as NSData
        
        self.score = defaults.integerForKey("QuizSession_Score")
        self.incorrectAnswers = defaults.integerForKey("QuizSession_IncorrectAnswers")
        self.timeCount = defaults.integerForKey("QuizSession_TimeCount")
        self.currentQuestion = NSKeyedUnarchiver.unarchiveObjectWithData(encodedQuestion) as? Question
        
        startTimer()
    }
    
    @objc func incrementTimer()
    {
        delegate.updateTimer(timeCount, timeLimit: timeLimit)
        timeCount++
    }
    
    func startTimer()
    {
        if timer == nil
        {
            // 3 Minute Timer
            self.timer = NSTimer.scheduledTimerWithTimeInterval(1, target: self, selector: "incrementTimer", userInfo: nil, repeats: true)
        }
        else
        {
            self.timer?.fire()
        }
    }
    
    func stopTimer()
    {
        self.timer?.invalidate()
        self.timer = nil
    }
    
    func nextQuestion()
    {
        self.currentQuestion = generateQuestion()
        
        delegate.updateQuestion()
    }
    
    func generateQuestion() -> Question
    {
        let rand = arc4random_uniform(9)
        
        switch rand
        {
        case 0:
            return DirectorQuestion()
        case 1:
            return ReleaseQuestion()
        case 2:
            return AppearedInQuestion()
        case 3:
            return NotAppearedInQuestion()
        case 4:
            return AppearTogetherQuestion()
        case 5:
            return DirectedQuestion()
        case 6:
            return NotDirectedQuestion()
        case 7:
            return AppearsInBothQuestion()
        case 8:
            return NotAppearsWithQuestion()
        case 9:
            return DirectedInYearQuestion()
        default:
            return generateQuestion()
        }
    }
    
    func currentAnswerCount() -> Int
    {
        return currentQuestion!.allPossibleAnswers.count
    }
    
    func currentAnswerSet() -> [String]
    {
        return currentQuestion!.allPossibleAnswers
    }
    
    func currentScore() -> Int
    {
        return score
    }
    
    func answerQuestionWithIndex(index: Int)
    {
        if currentQuestion!.correctAnswerIndex == index
        {
            score++
        }
        else
        {
            incorrectAnswers++
        }
        
        nextQuestion()
        
        delegate.updateScore()
        delegate.updateQuestion()
    }
}
