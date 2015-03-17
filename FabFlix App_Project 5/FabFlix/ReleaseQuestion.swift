//
//  ReleaseQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class ReleaseQuestion: Question
{
    override init() {
        super.init()
        
        var movie = super.getRandomMovie()
        
        let (result, err) = SD.executeQuery(
            "SELECT DISTINCT year FROM movies WHERE year!=? " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [movie.year])
        
        for row in result
        {
            if let ans = row["year"]?.asInt()
            {
                allPossibleAnswers.append(String(ans))
            }
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(String(movie.year), atIndex: correctAnswerIndex)
        title = "When was \(movie.title) released?"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}