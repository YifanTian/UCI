//
//  NotAppearedInQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class NotAppearedInQuestion: Question
{
    override init() {
        super.init()
    
        var movie = super.getRandomMovieWithMinStars(3)
        var answerStar : Star = Star()
        
        let (result, err) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name, stars.id AS s FROM stars " +
                "WHERE NOT EXISTS (SELECT stars.id FROM stars, stars_in_movies " +
                "WHERE stars_in_movies.star_id=s AND stars_in_movies.movie_id=?) " +
            "ORDER BY RANDOM() LIMIT 1;", withArgs: [movie.id])
        
        for row in result
        {
            let id: Int = (row["s"]?.asInt()!)!
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            answerStar = Star(id: id,firstName: firstName, lastName: lastName)
        }
        
        let (wrong, err2) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name FROM stars, stars_in_movies " +
            "WHERE stars_in_movies.star_id=stars.id AND stars_in_movies.movie_id=? " +
            "ORDER BY RANDOM() LIMIT 3;", withArgs: [movie.id])
        
        for row in wrong
        {
            var incorrectFirst = (row["first_name"]?.asString()!)!
            var incorrectLast = (row["last_name"]?.asString()!)!
            
            allPossibleAnswers.append("\(incorrectFirst) \(incorrectLast)")
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(answerStar.fullName, atIndex: correctAnswerIndex)
        title = "Which star did not appear in the movie \(movie.title)?"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}