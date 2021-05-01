import { Component, OnInit} from '@angular/core';
import{HttpClient} from '@angular/common/http';
import {map,switchMap} from 'rxjs/operators';
import { forkJoin } from 'rxjs';
export class Item {
  id:number=0;
  title:string='';
  poster_path:string='';
  type:string='';
}
@Component({
  selector: 'app-continue',
  templateUrl: './continue.component.html',
  styleUrls: ['./continue.component.css']
})
export class ContinueComponent implements OnInit {
  list:Item[]=[];
  flag=false;
  item!:Item;
  num:number=0;
  mobile=false;
  constructor(private http: HttpClient) {
    // localStorage.setItem("n","0");
   }

  ngOnInit() {
    if (window.screen.width === 414) { // 768px portrait
      this.mobile = true;
      console.log(this.mobile);
    }
    console.log(localStorage);
    if(localStorage.length>1){
      this.flag=true;
    }else{
      localStorage.setItem("n","0");
    }
    console.log(this.flag);
    // this.f2();
    this.f0();
  }
  f2(){
    this.num=0;
    for(var i=39;i>=0;i--){
      if(localStorage.hasOwnProperty(String(i))&&localStorage.getItem(String(i))!='null'){
        let idl=<string>localStorage.getItem(String(i));
        let id=idl.substring(1,idl.length);
        var url='';
        if(idl.substring(0,1)=='*'){
          url='http://localhost:3000/watching/';
        }else{
          url='http://localhost:3000/watchingtv/';
        }
        url+=id;
        console.log(id);
        this.http.get<any[]>(url).subscribe(response =>{
          this.list[this.num]=response[0];
          this.num=this.num+1;
          this.num=this.num+1;
          console.log(response[0]);
      });       
      }
    }
    console.log(this.list);
  } 
  f0(){
    const httpCalls = []; // store the requests here
    for(var i=39;i>=0;i--){
      if(localStorage.hasOwnProperty(String(i))&&localStorage.getItem(String(i))!='null'){
        let idl=<string>localStorage.getItem(String(i));
        let id=idl.substring(1,idl.length);
        var url='';
        if(idl.substring(0,1)=='*'){
          url='http://localhost:3000/watching/'+id;
        }else{
          url='http://localhost:3000/watchingtv/'+id;
        }
        httpCalls.push(this.http.get<Item[]>(url).pipe(map((response: Item[]) => response)));
      }
    }
    forkJoin(httpCalls).subscribe(res => {
      console.log(res);
      for(var i=0;i<res.length;i++){
      this.list[i]=res[i][0];
      }
      this.num=res.length;
      console.log(this.list);
    });
  }
}
