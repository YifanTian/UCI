//
//  DirectedQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class DirectedQuestion: Question
{
    override init() {
        super.init()
        
        let movie = super.getRandomMovie()
        var answerStar: Star = Star()
        
        let(result, error) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name, stars.id FROM stars, stars_in_movies " +
            "WHERE stars_in_movies.movie_id=? AND stars_in_movies.star_id=stars.id " +
            "ORDER BY RANDOM() LIMIT 1;",
            withArgs: [movie.id])
        
        for row in result
        {
            let id: Int = (row["id"]?.asInt()!)!
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            answerStar = Star(id: id, firstName: firstName, lastName: lastName)
        }
        
        let (wrong, error2) = SD.executeQuery(
            "SELECT director, movies.id AS m FROM movies " +
            "WHERE NOT EXISTS(SELECT star_id FROM stars_in_movies " +
            "WHERE star_id=? AND movie_id=m) " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [answerStar.id])
        
        for row in wrong
        {
            let wrongDirector: String = (row["director"]?.asString()!)!
            
            allPossibleAnswers.append("\(wrongDirector)")
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(movie.director, atIndex: correctAnswerIndex)
        title = "Who directed \(answerStar.fullName)?"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}