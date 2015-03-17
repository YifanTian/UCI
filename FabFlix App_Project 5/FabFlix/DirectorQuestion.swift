//
//  DirectorQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class DirectorQuestion: Question
{
    override init() {
        super.init()
        
        var movie = super.getRandomMovie()
        
        let (result, err) = SD.executeQuery(
            "SELECT director FROM movies WHERE director != ? " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [movie.director])
        
        for row in result
        {
            if let ans = row["director"]?.asString()
            {
                allPossibleAnswers.append(ans)
            }
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(movie.director, atIndex: correctAnswerIndex)
        title = "Who directed the movie \(movie.title)?"
    }

    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}