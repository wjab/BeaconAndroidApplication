//
//  HistoryPointsViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/16/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

import Alamofire
import SwiftyJSON

class HistoryPointsViewController:  UIViewController, UITableViewDelegate, UITableViewDataSource
{
    var utilsC = UtilsC()
    var historyArray: [History] = []
    var actualyArrayIndex = 0
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "CellHistoryPoints"
    let defaults = NSUserDefaults.standardUserDefaults()
    var idUser = ""
    var btn1 = UIButton()
    var wishCount = 1
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        
        self.idUser = (defaults.objectForKey("userId") as? String)!
        service()
        table.delegate = self
        table.dataSource = self
        let points = defaults.objectForKey("points") as! Int
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        //Cambia el tamaño de los tabs
        //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
        btn1 = Utils.loadWishListButton(btn1, wishCount: wishCount)
        btn1.addTarget(self, action: #selector(HomeTabViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        
        //Button abre  menu
        _ = UIButton()
        _ = defaults.objectForKey("image")as! String
        _ = defaults.objectForKey("socialNetworkType")as! String
        
        // Crea el view con el label de puntos y el arrow de imagen
        let myView = Utils.createPointsView(points, activateEvents: true)
        self.navigationItem.titleView = myView
        
         NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(HistoryPointsViewController.refreshWishCount),name:"refreshWishCountHistory", object: nil)
    }
    //Refresca el contador
    func refreshWishCount(){
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        btn1.setTitle(String(self.wishCount), forState: .Normal)
    }
    
    //Abre el menu
    func openMenu()
    {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("MenuContainerViewController") as! MenuContainerViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    
    //Abre el historial de puntos
    func clickOnButton()
    {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("HistoryPointsViewController") as! HistoryPointsViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre la lista de deseos
    func openWishList()
    {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("WishViewController") as! WishViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }

    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func service()
    {
        //Endpoint
        let url : String = Constants.ws_services.utils + "user/getPointsData/" + self.idUser
        //Crea el request
        print(url)
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                    //Si la respuesta es satisfactoria
                    case .Success(let JSON):
                        let response = JSON as! NSDictionary
                        _ = JSON as! NSDictionary
                        //Si la respuesta no tiene status 404
                        if(String((response)["status"] as! Int) == Constants.ws_response_code.ok)
                        {
                            print((response)["status"])
                            
                            let productList = response.mutableArrayValueForKey("pointsData")
                            for (_, history) in productList.enumerate()
                            {
                                //Obtiene los datos de la tienda
                                let shop = history.objectForKey("merchantProfile")
                                //Obtiene los datos de la promocion
                                let promo = history.objectForKey("promo")
                                //Obtiene la direccion de la tienda
                                let address = shop!.valueForKey("address")as! String
                                //Obtiene el titulo de la promocion
                                let title = promo!.valueForKey("title")as! String
                                //Obtiene los punts obtenidos
                                let points = history.valueForKey("points") as! Int
                                //Obtiene el ultimo escaneo
                                let scanDate = history.valueForKey("lastScanDate") as! Int
                                //Crea el nuevo objeto Historial
                                let historyObject = History()
                                
                                //Settea los datos al objeto Historial
                                historyObject.addressShopPropeties = address
                                historyObject.pointsPropeties = points
                                historyObject.promoTitlePropeties = title
                                historyObject.scanDate = scanDate
                                self.historyArray.append(historyObject)
                            }
                            self.table.reloadData()
                        }
                        else
                        {
                            print("Hubo un error obteniendo los datos de lista de deseos")
                        }
                    case .Failure(let error):
                        print("Hubo un error realizando la peticion: \(error)")
                }
            }
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return self.historyArray.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell
    {
        let cell:HistoryPointsCell = self.table.dequeueReusableCellWithIdentifier(cellReuseIdentifier) as! HistoryPointsCell!
        let historyObject = self.historyArray[indexPath.row]
        let name = historyObject.promoTitlePropeties
        let addressShop = historyObject.addressShopPropeties
        let dateFloat = historyObject.scanDatePropeties
        let points = historyObject.pointsPropeties
        cell.configure(name, points: points ,address: addressShop, date: dateFloat )
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath)
    {
        self.actualyArrayIndex = indexPath.row
    }
    
    
}

