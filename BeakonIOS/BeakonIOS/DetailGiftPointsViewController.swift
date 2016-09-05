//
//  DetailGiftPointsViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 9/5/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class DetailGiftPointsViewController: UIViewController {
    @IBOutlet weak var codeL: UILabel!
    @IBOutlet weak var messageL: UILabel!
    var code = ""
    var message = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        self.codeL.text = self.code
        self.messageL.text = self.message
    }
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
