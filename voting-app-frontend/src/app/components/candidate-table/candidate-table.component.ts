import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CandidateData } from 'src/app/datatypes/candidate-data';
import { VotingService } from 'src/app/services/voting.service';
import { GridComponent } from '../grid/grid.component';

@Component({
  selector: 'app-candidate-table',
  templateUrl: './candidate-table.component.html',
})
export class CandidateTableComponent implements OnInit {
  columnDefs = [
    { field: 'name', headerName: 'Candidate name' },
    {
      field: 'votes',
      headerName: 'Number of votes',
      valueFormatter: GridComponent.numberFormatter,
    },
  ];
  rowData: Array<CandidateData> = null!;

  @Output() onNewCandidate = new EventEmitter();

  constructor(
    private votingService: VotingService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.refreshCandidates();
  }

  createCandidate(name: string): void {
    this.votingService.createCandidate(name).subscribe({
      next: (response: string) => {
        this.toastr.success(response, 'Success');
        this.refreshCandidates();
        this.onNewCandidate.emit();
      },
      error: (errorResponse: HttpErrorResponse) =>
        this.toastr.error(errorResponse.error, 'Error'),
    });
  }

  refreshCandidates(): void {
    this.votingService.getCandidates().subscribe((data) => {
      this.rowData = data;
    });
  }
}
