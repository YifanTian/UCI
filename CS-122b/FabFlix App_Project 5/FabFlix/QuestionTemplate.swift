//
//  Question.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

protocol QuestionTemplate
{
    var title: String { get }
    
    var correctAnswerIndex: Int { get }
    
    var allPossibleAnswers: [String] { get }
}
