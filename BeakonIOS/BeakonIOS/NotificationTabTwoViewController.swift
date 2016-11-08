//
//  NotificationTabTwoViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/26/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
//import Haneke

class NotificationTabTwoViewController: UIViewController , UITableViewDelegate, UITableViewDataSource{
    var notificationArray: [Notification] = []
    var actualyArrayIndex = 0
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellNewNotificationAll"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        service()
        self.table.registerClass(UITableViewCell.self, forCellReuseIdentifier: cellReuseIdentifier)
        table.delegate = self
        table.dataSource = self
        
    }
    
    func service(){
        let defaults = NSUserDefaults.standardUserDefaults()
        let userId = defaults.objectForKey("userId") as? String
        //Endpoint
        let url : String = "http://butilsdevel.cfapps.io/notification/all/"+userId!
        //Crea el request
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! String == Constants.ws_response_code.ok)
                    {
                        let notificationList = response.mutableArrayValueForKey("notificationResult")
                        for (_, element) in notificationList.enumerate() {
                            let notificationObject = Notification()
                            notificationObject.idPropeties = element.objectForKey("id") as! String
                            notificationObject.messagePropeties = element.objectForKey("message") as! String
                            notificationObject.userIdPropeties = element.objectForKey("userId") as! String
                            notificationObject.typePropeties = element.objectForKey("type") as! String
                            notificationObject.readPropeties = element.objectForKey("read") as! Bool
                            notificationObject.creationDatePropeties = element.objectForKey("creationDate") as! Float
                            self.notificationArray.append(notificationObject)
                        }
                        self.table.reloadData()
                    }
                    else
                    {
                        print(Constants.error_messages.error_notification)
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.notificationArray.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell = self.table.dequeueReusableCellWithIdentifier(cellReuseIdentifier) as UITableViewCell!
        let notification = self.notificationArray[indexPath.row]
        cell.textLabel?.text = String(notification.messagePropeties)
        cell.textLabel!.font = UIFont.systemFontOfSize(10.0)
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let messageData = self.notificationArray[self.actualyArrayIndex].messagePropeties
        let alertController = UIAlertController(title: "QuickShop", message:
            messageData, preferredStyle: UIAlertControllerStyle.Alert)
        alertController.addAction(UIAlertAction(title: "Cerrar", style: UIAlertActionStyle.Default,handler: nil))
        
        self.presentViewController(alertController, animated: true, completion: nil)    }
    
}
