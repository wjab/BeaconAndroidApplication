//
//  FAQTabThreeViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/2/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class FAQTabThreeViewController: UIViewController ,UITableViewDelegate, UITableViewDataSource{
    var array: [FAQ] = []
    var actualyArrayIndex = 0
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellFAQThree"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        service()
        self.table.registerClass(UITableViewCell.self, forCellReuseIdentifier: cellReuseIdentifier)
        table.delegate = self
        table.dataSource = self
        
    }
    
    func service(){
        //Endpoint
        let url : String = "http://bfaqdevel.cfapps.io/faq/section/deseos/"
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
                    if((response)["status"] as! Int != 404)
                    {
                        print(response.objectForKey("status"))
                        let faqList = response.mutableArrayValueForKey("faq")
                        for (index, element) in faqList.enumerate() {
                            let faqObject = FAQ()
                            faqObject.questionPropeties = element.objectForKey("question") as! String
                            faqObject.answerPropeties = element.objectForKey("answer") as! String
                            print(index)
                            self.array.append(faqObject)
                        }
                        self.table.reloadData()
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de preguntas frecuentes")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.array.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell = self.table.dequeueReusableCellWithIdentifier(cellReuseIdentifier) as UITableViewCell!
        let faq = self.array[indexPath.row]
        cell.textLabel?.text = String(faq.question)
        cell.textLabel!.font = UIFont.systemFontOfSize(10.0)
        return cell
    }
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let messageData = self.array[self.actualyArrayIndex].answerPropeties
        let title = self.array[self.actualyArrayIndex].questionPropeties
        let alertController = UIAlertController(title: title, message:
            messageData, preferredStyle: UIAlertControllerStyle.Alert)
        alertController.addAction(UIAlertAction(title: "Cerrar", style: UIAlertActionStyle.Default,handler: nil))
        self.presentViewController(alertController, animated: true, completion: nil)
    }
    
    
}