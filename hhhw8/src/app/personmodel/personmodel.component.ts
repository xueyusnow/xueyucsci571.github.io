import { Component, OnInit, Input , Output, EventEmitter} from '@angular/core';
import {DataService} from '../data.service';
 
@Component({
  selector: 'app-personmodel',
  templateUrl: './personmodel.component.html',
  styleUrls: ['./personmodel.component.css']
})
export class PersonmodelComponent implements OnInit {
  @Input() iid:number=0;
  person:any=[];
  @Output() personget: EventEmitter<number> =   new EventEmitter();
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.f1();

  }
  f1(){
    var url='http://localhost:3000/person/';
    url+=this.iid;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.person=data;
      this.personget.emit(this.person);
    });
  }
  
}
