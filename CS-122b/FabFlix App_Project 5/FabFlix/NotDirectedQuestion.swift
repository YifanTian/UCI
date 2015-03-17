//
//  NotDirectedQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class NotDirectedQuestion: Question
{
    override init() {
        super.init()
        
        var answerStar: Star = Star()
        var answerDirector = String()
        
        let (result, error) = SD.executeQuery(
            "SELECT first_name, last_name, id FROM stars WHERE (SELECT COUNT(DISTINCT director) " +
                "FROM stars_in_movies AS s, movies AS m WHERE stars.id=s.star_id AND m.id=s.movie_id) >= 3 " +
            "ORDER BY RANDOM() LIMIT 1;")
        
        for row in result
        {
            let id: Int = (row["id"]?.asInt()!)!
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            answerStar = Star(id: id,firstName: firstName, lastName: lastName)
        }
        
        let (resultDirector, error2) = SD.executeQuery(
            "SELECT director FROM movies AS m, stars_in_movies AS s " +
            "WHERE NOT EXISTS (SELECT stars_in_movies.movie_id FROM stars_in_movies " +
                "WHERE stars_in_movies.movie_id=m.id AND stars_in_movies.star_id=?) " +
            "ORDER BY RANDOM() LIMIT 1;",
            withArgs: [answerStar.id])
        
        for row in resultDirector
        {
            answerDirector = (row["director"]?.asString()!)!
        }
        
        let (result3, error3) = SD.executeQuery(
            "SELECT DISTINCT director FROM movies NATURAL JOIN stars_in_movies " +
            "WHERE stars_in_movies.star_id=? AND stars_in_movies.movie_id=id " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [answerStar.id])
        
        for row in result3
        {
            let director: String = (row["director"]?.asString()!)!
            
            allPossibleAnswers.append(director)
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert( answerDirector, atIndex: correctAnswerIndex)
        title = "Who did not direct \(answerStar.fullName)?"
    }

    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}