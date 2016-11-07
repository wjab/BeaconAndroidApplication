//
//  ScanViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 10/3/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import AVFoundation
import Alamofire
import SwiftyJSON
import JLToast

class ScanViewController: UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    var shopId = ""
    var code = ""
    let session = AVCaptureSession()
    var previewLayer : AVCaptureVideoPreviewLayer?
    var  identifiedBorder : DiscoveredBarCodeView?
    var timer : NSTimer?
    let defaults = NSUserDefaults.standardUserDefaults()
    
    /* Add the preview layer here */
    func addPreviewLayer() {
        previewLayer = AVCaptureVideoPreviewLayer(session: session)
        previewLayer?.videoGravity = AVLayerVideoGravityResizeAspectFill
        previewLayer?.bounds = self.view.bounds
        previewLayer?.position = CGPointMake(CGRectGetMidX(self.view.bounds), CGRectGetMidY(self.view.bounds))
        self.view.layer.addSublayer(previewLayer!)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        var error : NSError?
        do {
            let captureDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
            let input = try AVCaptureDeviceInput(device: captureDevice)
            session.addInput(input)
        } catch let error as NSError {
            print(error)
        }
        
        addPreviewLayer()
        
        identifiedBorder = DiscoveredBarCodeView(frame: self.view.bounds)
        identifiedBorder?.backgroundColor = UIColor.clearColor()
        identifiedBorder?.hidden = true;
        self.view.addSubview(identifiedBorder!)
        let output = AVCaptureMetadataOutput()
        session.addOutput(output)
        output.metadataObjectTypes = output.availableMetadataObjectTypes
        output.setMetadataObjectsDelegate(self, queue: dispatch_get_main_queue())
        session.startRunning()
    }
    
    //Envio del request para obtener puntos mediante escaneo
    func requestScanCode(){
        let url : String = Constants.ws_services.utils+"savePointsByCode"
        let idUser = (defaults.objectForKey("userId") as? String)!
        let newTodo = [
            "userId": idUser,
            "merchantId": shopId,
            "code": code
        ]
        print("userId "+idUser+" merchantId "+shopId+" code "+code)
        Alamofire.request(.POST, url, parameters: newTodo, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    var user = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if(String((response)["status"] as! Int) == Constants.ws_response_code.ok)
                    {
                        JLToast.makeText("Puntos obtenidos").show()
                        user = response.objectForKey("user")! as! NSDictionary
                        let defaults = NSUserDefaults.standardUserDefaults()
                        defaults.setObject((user)["totalGiftPoints"] as! Int, forKey: "points")
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshPoints", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshPointsHome", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshPointsDepartment", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshPointsShop", object: nil)
                    }
                    else if((response)["status"] as! Int == 400){
                        JLToast.makeText((response)["message"] as! String).show()
                    }
                    else
                    {
                        JLToast.makeText("Problemas al momento de realizar el canjeo de puntos por escaneo").show()
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
    override func viewWillAppear(animated: Bool) {
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillDisappear(animated: Bool) {
        session.stopRunning()
    }
    
    func translatePoints(points : [AnyObject], fromView : UIView, toView : UIView) -> [CGPoint] {
        var translatedPoints : [CGPoint] = []
        for point in points {
            let dict = point as! NSDictionary
            let x = CGFloat((dict.objectForKey("X") as! NSNumber).floatValue)
            let y = CGFloat((dict.objectForKey("Y") as! NSNumber).floatValue)
            let curr = CGPointMake(x, y)
            let currFinal = fromView.convertPoint(curr, toView: toView)
            translatedPoints.append(currFinal)
        }
        
        return translatedPoints
    }
    
    
    func startTimer() {
        if timer?.valid != true {
            timer = NSTimer.scheduledTimerWithTimeInterval(0.2, target: self, selector: #selector(ScanViewController.removeBorder), userInfo: nil, repeats: false)
        } else {
            timer?.invalidate()
        }
    }
    
    func removeBorder() {
        self.identifiedBorder?.hidden = true
    }
    
    func captureOutput(captureOutput: AVCaptureOutput!, didOutputMetadataObjects metadataObjects: [AnyObject]!, fromConnection connection: AVCaptureConnection!) {
        var capturedBarcode: String
        let supportedBarcodeTypes = [AVMetadataObjectTypeUPCECode, AVMetadataObjectTypeCode39Code, AVMetadataObjectTypeCode39Mod43Code,
                                     AVMetadataObjectTypeEAN13Code, AVMetadataObjectTypeEAN8Code, AVMetadataObjectTypeCode93Code, AVMetadataObjectTypeCode128Code,
                                     AVMetadataObjectTypePDF417Code, AVMetadataObjectTypeQRCode, AVMetadataObjectTypeAztecCode]
        
     
        for barcodeMetadata in metadataObjects {
            
            for supportedBarcode in supportedBarcodeTypes {
                
                if supportedBarcode == barcodeMetadata.type {
             
                    let barcodeObject = self.previewLayer!.transformedMetadataObjectForMetadataObject(barcodeMetadata as! AVMetadataObject)
                    capturedBarcode = (barcodeObject as! AVMetadataMachineReadableCodeObject).stringValue
                    self.code = capturedBarcode
                    self.requestScanCode()
                    self.session.stopRunning()
                    return
                }
            }
        }
    }
    
}
