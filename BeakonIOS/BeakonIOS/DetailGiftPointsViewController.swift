//
//  DetailGiftPointsViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/5/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class DetailGiftPointsViewController: UIViewController {
    @IBOutlet weak var share: UIButton!
    @IBOutlet weak var codeL: UILabel!
    @IBOutlet weak var messageL: UILabel!
    @IBOutlet weak var date: UILabel!
    var code = ""
    var message = ""
    var expiration = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.codeL.text = self.code
        self.messageL.text = self.message
        self.date.text = String(self.expiration)
        share.addTarget(self, action: #selector(DetailGiftPointsViewController.shareCode), forControlEvents: .TouchUpInside)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshPoints", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshPointsHome", object: nil)
    }
    
  override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func shareCode(){
        let code = codeL.text
        let activityViewController = UIActivityViewController(activityItems: [ code! as NSString], applicationActivities: nil)
        presentViewController(activityViewController, animated: true, completion: {})
        
    }

}
