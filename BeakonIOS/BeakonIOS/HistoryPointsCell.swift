//
//  HistoryPointsCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/16/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class HistoryPointsCell: UITableViewCell {

    @IBOutlet weak var pointsL: UILabel!
    @IBOutlet weak var dateL: UILabel!
    @IBOutlet weak var shopNameL: UILabel!
    @IBOutlet weak var addressL: UILabel!

    internal func configure(name:String, points:Int, address:String, date:Float) {
        pointsL.text = String(points)
        shopNameL.text = name
        addressL.text = address
        dateL.text = String(date)
    }
}
