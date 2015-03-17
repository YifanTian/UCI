//
//  AnswerTableViewCell.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import UIKit

class AnswerTableViewCell: UITableViewCell
{
    @IBOutlet weak var buttonBackingView: UIView!
    @IBOutlet weak var answerTitleLabel: UILabel!
    
    let blueColor: UIColor = UIColor(red: 13/255, green: 122/255, blue: 254/255, alpha: 1)
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        self.buttonBackingView.layer.cornerRadius = 5
        self.buttonBackingView.clipsToBounds = true
        self.buttonBackingView.layer.borderWidth = 1
        
        self.buttonBackingView.layer.borderColor = blueColor.CGColor
        self.buttonBackingView.backgroundColor = UIColor.whiteColor()
        
        self.answerTitleLabel.textColor = blueColor
    }
    
    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        if selected
        {
            self.buttonBackingView.backgroundColor = blueColor
            
            self.answerTitleLabel.textColor = UIColor.whiteColor()
        }
        else
        {
            self.buttonBackingView.backgroundColor = UIColor.whiteColor()
            
            self.answerTitleLabel.textColor = blueColor
        }
    }
    
}