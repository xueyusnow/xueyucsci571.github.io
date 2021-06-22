import { Component, OnInit} from '@angular/core';
import{HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import { forkJoin } from 'rxjs';
export class Item {
  id:number=0;
  title:string='';
  poster_path:string='';
  type:string='';
}
@Component({
  selector: 'app-mylist',
  templateUrl: './mylist.component.html',
  styleUrls: ['./mylist.component.css']
})
export class MylistComponent implements OnInit {
  list:Item[]=[];
  num:number=0;
  helper:any[]=[];
  mobile=false;
  constructor(private http: HttpClient) {
   }

  ngOnInit() {
    if (window.screen.width === 414) { // 768px portrait
      this.mobile = true;
      console.log(this.mobile);
    }
    this.f3();
    this.f0();
  }
  f3(){
    var a=[];
    for(var i=0;i<localStorage.length;i++){
      if(localStorage.key(i)!='null'&&((<string>localStorage.key(i)).substring(0,1)=='*')||((<string>localStorage.key(i)).substring(0,1)=='#')){
        // var len=(<string>localStorage.key(i)).length;
        // var key=(<string>localStorage.key(i)).substring(1,len);
        var key=<string>localStorage.key(i);
          a.push([Number(localStorage.getItem(key)),key]);
      }
    }
    a.sort(this.sortfunc);
    this.helper=a;
    console.log(this.helper);
  }
  sortfunc(a:any,b:any){
    if (a[0] === b[0]) {
      return 0;
    }
    else {
      return (a[0] < b[0]) ? 1 : -1;
    }
  }
  f0(){
    const httpCalls = []; // store the requests here
    for(var i=0;i<this.helper.length;i++){
      let idl=this.helper[i][1];
      let id=idl.substring(1,idl.length);
      var url='';
      if(idl.substring(0,1)=='*'){
        url='http://localhost:3000/watching/'+id;
      }else{
        url='http://localhost:3000/watchingtv/'+id;
      }
      httpCalls.push(this.http.get<Item[]>(url).pipe(map((response: Item[]) => response)));
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
