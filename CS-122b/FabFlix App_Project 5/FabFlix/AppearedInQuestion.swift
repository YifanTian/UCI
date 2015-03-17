//
//  AppearedInQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class AppearedInQuestion: Question
{
    override init() {
        super.init()
        
        var movie = super.getRandomMovie()
        var answerStar: Star = Star()
        
        let (result, err) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name FROM stars, stars_in_movies " +
            "WHERE stars_in_movies.movie_id=? AND stars_in_movies.star_id=stars.id " +
            "ORDER BY RANDOM() LIMIT 1;",
            withArgs: [movie.id])
        
        for row in result
        {
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            answerStar = Star(firstName : firstName, lastName: lastName)
        }
        
        let (wrong, err2) = SD.executeQuery(
            "SELECT first_name, last_name FROM stars, stars_in_movies " +
            "WHERE first_name!=? AND stars.last_name!=? AND stars_in_movies.star_id=stars.id " +
            "AND stars_in_movies.movie_id!=? ORDER BY RANDOM() LIMIT 3;",
            withArgs: [answerStar.firstName, answerStar.lastName, movie.id])
       
        for row in wrong
        {
            let incorrectFirst: String = (row["first_name"]?.asString()!)!
            let incorrectLast: String = (row["last_name"]?.asString()!)!
            
            allPossibleAnswers.append("\(incorrectFirst) \(incorrectLast)")
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(answerStar.fullName, atIndex: correctAnswerIndex)
        title = "Which star appears in the movie \(movie.title)?"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}