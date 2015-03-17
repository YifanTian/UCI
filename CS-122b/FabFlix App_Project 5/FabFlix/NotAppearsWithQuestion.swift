//
//  NotAppearsWithQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class NotAppearsWithQuestion: Question
{
    override init() {
        super.init()
        
        var answerMovieID: Int = 0
        var starTwo : Star = Star()
        var starOne : Star = Star()
        
        let (result, error) = SD.executeQuery(
            "SELECT movies.id from movies, stars_in_movies " +
                "WHERE (SELECT count (distinct stars.last_name) from stars, stars_in_movies " +
                "WHERE stars_in_movies.movie_id=movies.id and stars_in_movies.star_id = stars.id) >= 4 " +
            "ORDER BY RANDOM() LIMIT 1;")
        
        for row in result
        {
            answerMovieID = (row["id"]?.asInt()!)!
        }
        
        let (resultStarOne, error2) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name from stars, stars_in_movies " +
            "WHERE stars_in_movies.star_id=stars.id AND stars_in_movies.movie_id = ? " +
            "ORDER BY RANDOM() LIMIT 1;",
            withArgs: [answerMovieID])
        
        for row in resultStarOne
        {
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            starOne = Star(firstName: firstName, lastName: lastName)
        }
        
        let (resultStarTwo, error3) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name FROM stars, stars_in_movies " +
            "WHERE stars_in_movies.star_id=stars.id AND stars_in_movies.movie_id!=? " +
            "ORDER BY RANDOM() LIMIT 1;",
            withArgs: [answerMovieID])
        
        for row in resultStarTwo
        {
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            starTwo = Star(firstName: (row["first_name"]?.asString()!)!, lastName: (row["last_name"]?.asString()!)!)
        }
        
        let (result4, error4) = SD.executeQuery(
            "SELECT distinct stars.first_name, stars.last_name from stars, stars_in_movies " +
            "WHERE stars_in_movies.star_id=stars.id AND stars_in_movies.movie_id=? AND stars.first_Name != ? AND stars.last_name != ? " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [answerMovieID, starOne.firstName, starOne.lastName])
        
        for row in result4
        {
            let incorrectFirst = (row["first_name"]?.asString()!)!
            let incorrectLast = (row["last_name"]?.asString()!)!
            
            allPossibleAnswers.append("\(incorrectFirst) \(incorrectLast)")
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(starTwo.fullName, atIndex: correctAnswerIndex)
        title = "Which star did not appear in the same movie as \(starOne.fullName)?"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}