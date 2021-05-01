import { Component, OnInit, Input , Output, EventEmitter} from '@angular/core';
import {DataService} from '../data.service';
@Component({
  selector: 'app-personextmodel',
  templateUrl: './personextmodel.component.html',
  styleUrls: ['./personextmodel.component.css']
})
export class PersonextmodelComponent implements OnInit {
  @Input() iid:number=0;
  personext:any=[];
  @Output() personextget: EventEmitter<number> =   new EventEmitter();
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.f1();

  }
  f1(){
    var url='http://localhost:3000/personexter/';
    url+=this.iid;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.personext=data;
      this.personextget.emit(this.personext);
    });
  }
  
}
