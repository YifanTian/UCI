//
//  QuestionViewController.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import UIKit

class QuestionViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UINavigationBarDelegate, QuizSessionManagerProtocol
{
    @IBOutlet weak var navBar: UINavigationBar!
    
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var tableView: UITableView!
    
    @IBOutlet weak var scoreLabel: UILabel!
    
    lazy var quizManager: QuizSessionManager = QuizSessionManager(delegate: self)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        quizManager.nextQuestion()
        
        self.scoreLabel.layer.cornerRadius = 5
        self.scoreLabel.clipsToBounds = true
        
        self.scoreLabel.backgroundColor = UIColor(red: 13/255, green: 122/255, blue: 254/255, alpha: 1)
        self.scoreLabel.textColor = UIColor.whiteColor()
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        
        quizManager.startTimer()
    }
    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
        
        quizManager.stopTimer()
    }
    
    func updateTimer(timeCount: Int, timeLimit: Int)
    {
        var timeCountCopy = timeLimit - timeCount
        
        let minutes = timeCountCopy / 60
        timeCountCopy -= minutes * 60
        
        let seconds = timeCountCopy
        
        let minutesString = minutes > 9 ? String(minutes):"0" + String(minutes)
        let secondsString = seconds > 9 ? String(seconds):"0" + String(seconds)
        
        self.navBar.topItem?.title = "\(minutesString):\(secondsString)"
        
        if quizManager.timeCount >= quizManager.timeLimit
        {
            performSegueWithIdentifier("QuizTimerElapsedSegueID", sender: self)
        }
    }
    
    func updateQuestion()
    {
        self.tableView.reloadData()
        self.questionLabel.text = self.quizManager.currentQuestion!.title
    }
    
    func updateScore()
    {
        self.scoreLabel.text = "Score: \(quizManager.score)"
    }
    
    @IBAction func endQuiz(sender: AnyObject)
    {
        performSegueWithIdentifier("QuizTimerElapsedSegueID", sender: self)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if segue.identifier == "QuizTimerElapsedSegueID"
        {
            quizManager.stopTimer()
            
            let incorrect: Int = quizManager.incorrectAnswers
            let correct: Int = quizManager.score
            let totalQuestions: Int = incorrect + correct
        
            // Protecting against divide by zero
            var avgTimeSpent: Int = (totalQuestions > 0) ? quizManager.timeCount / totalQuestions : 0
        
            var avgTimeSpendCopy = avgTimeSpent
            
            let minutes = avgTimeSpendCopy / 60
            avgTimeSpendCopy -= minutes * 60
            
            let seconds = avgTimeSpendCopy
            
            let minutesString = minutes > 9 ? String(minutes):"0" + String(minutes)
            let secondsString = seconds > 9 ? String(seconds):"0" + String(seconds)
            
            let avgTimeSpentString: String = "\(minutesString):\(secondsString)"
            
            StatisticsManager.commitResult(correct, incorrectCount: incorrect, averageTime: avgTimeSpent)
            
            let viewController: ResultsViewController = segue.destinationViewController as ResultsViewController
            viewController.values = ["\(correct)", "\(incorrect)", avgTimeSpentString]
        }
    }
    
    // MARK: UITableViewDataSource
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return quizManager.currentAnswerCount() // Number of answers
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("AnswerCell") as AnswerTableViewCell
        
        cell.answerTitleLabel.text = quizManager.currentAnswerSet()[indexPath.row] as String
        
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        
        var dispatchTime: dispatch_time_t = dispatch_time(DISPATCH_TIME_NOW, Int64(0.3 * Double(NSEC_PER_SEC)))
        dispatch_after(dispatchTime, dispatch_get_main_queue(), {
            
            self.quizManager.answerQuestionWithIndex(indexPath.row)
        })
    }
    
    // MARK: UINavigationBarDelegate
    
    func positionForBar(bar: UIBarPositioning) -> UIBarPosition {
        return UIBarPosition.TopAttached
    }
}