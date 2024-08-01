import { Component, OnInit } from '@angular/core';
import { MenuItem, TreeNode, TreeTableNode } from 'primeng/api';
import { FileUploadService } from 'src/services/file.service';
import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef } from '@angular/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'file-archive',
  templateUrl: './filearchive.component.html',
  providers: [MessageService]
})
export class FileArchiveComponent implements OnInit {
  cols!: any[];
  selectedFiles: File[] = [];
  message: string = '';
  selectedNode?: TreeNode;
  selectedNode2?: TreeTableNode<any> | TreeTableNode<any>[] | null;
  files: TreeNode[] = [];
  newDirectoryName: string = '';
  tree: TreeNode[] = [];
  parentPath: string | null = null;
  id: number = 1;
  items: MenuItem[] | undefined;
  private apiUrl = 'http://localhost:8090/bfi/v1';
  selectionKeys: any;

  constructor(
    private fileUploadService: FileUploadService,
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.cols = [
      { field: 'name', header: 'Name' },
      { field: 'size', header: 'Size' },
      { field: 'filetype', header: 'Type' }
    ];

    this.items = [
      {
        label: 'Update',
        icon: 'pi pi-refresh'
      },
      {
        label: 'Delete',
        icon: 'pi pi-times'
      },
      {
        label: 'Angular',
        icon: 'pi pi-external-link',
        url: 'http://angular.io'
      },
      {
        label: 'Router',
        icon: 'pi pi-upload',
        routerLink: '/fileupload'
      }
    ];

    this.fetchFilesAndDirectories();
  }

  onFileSelected(event: any) {
    this.selectedFiles = Array.from(event.files);
  }

  onUpload() {
    if (this.selectedFiles.length > 0) {
      this.selectedFiles.forEach(file => {
        const uploadId = this.id;
        this.fileUploadService.upload(file, uploadId).subscribe({
          next: (response: any) => {
            console.log('Upload response:', response);
            this.message = 'File uploaded successfully';
            this.fetchFilesAndDirectories();
          },
          error: (err: any) => {
            console.error('Upload error:', err);
            this.message = 'Failed to upload file';
          }
        });
      });
      this.selectedFiles = [];
    } else {
      this.message = 'No files selected for upload';
    }
  }

  fetchFilesAndDirectories() {
    this.fileUploadService.getAllFilesAndDirectories().subscribe({
      next: (response: any) => {
        const { files, directories } = response;
        console.log("Fetched files and directories:", files, directories);
        this.tree = this.buildFileTree(files, directories);
        console.log("Built tree:", this.tree);
        this.files = [...this.tree];
        this.cdr.detectChanges();
      },
      error: error => {
        console.error("Error fetching files and directories:", error);
      }
    });
  }

  buildFileTree(files: any[], directories: any[]): TreeNode[] {
    const map = new Map<string, TreeNode>();

    const createNode = (item: any): TreeNode => ({
      data: {
        id: item.id,
        name: item.name,
        size: item.size || '',
        filetype: item.filetype || 'directory'
      },
      children: [],
      expanded: true,
      selectable: true
    });

    directories.forEach(directory => {
      const path = directory.path;
      console.log("Map:", map);
      const node = createNode(directory);
      map.set(path, node);
    });

    const rootNode: TreeNode = {
      data: { id: 1, name: 'Root', size: '', filetype: 'directory' },
      children: [],
      expanded: true,
      selectable: true
    };

    map.set('root', rootNode);

    files.forEach(file => {
      const path = file.path;
      if (!map.has(path)) {
        const node = createNode(file);
        map.set(path, node);
      } else {
        const existingNode = map.get(path);
        if (existingNode) {
          const parentPath = path;
          const parentNode = map.get(parentPath);
          if (parentNode) {
            const fileNode = createNode(file);
            parentNode.children = parentNode.children ?? [];
            if (!parentNode.children.find(child => child.data.name === fileNode.data.name)) {
              parentNode.children.push(fileNode);
            }
          } else {
            console.error(`Parent node not found for path: ${parentPath}`);
          }
        }
      }
    });

    map.forEach((node, path) => {
      const item = directories.find(d => d.path === path) || files.find(f => f.path === path);
      if ((item && (item.parent_id || item.directory_id)) && (item.path !== 'root')) {
        let parentPath = item.path;
        if (item.parent_id) {
          parentPath = this.getParentPath(item.path);
        }
        const parentNode = map.get(parentPath!);
        if (parentNode) {
          parentNode.children = parentNode.children ?? [];
          if (!parentNode.children.find(child => child.data.name === node.data.name)) {
            parentNode.children.push(node);
          }
        } else {
          console.error(`Parent node not found for path: ${parentPath}`);
        }
      } else {
        if (path !== 'root') {
          if (!rootNode.children!.find(n => n.data.name === node.data.name)) {
            rootNode.children!.push(node);
          }
        }
      }
    });

    console.log("Map of nodes:", Array.from(map.entries()).map(([key, value]) => ({ key, value: value.data.name })));
    console.log("Final rootNode:", rootNode);

    return [rootNode];
  }

  onNodeSelect(event: any) {
    this.selectedNode = event.node;
    this.id = event.node.data.id;
    console.log(this.id);
    console.log(event.node.data.name);
  }

  createDirectory() {
    if (!this.newDirectoryName.trim()) {
      this.message = 'Directory name cannot be empty';
      return;
    }

    const directoryName = this.newDirectoryName.trim();
    const parentDirectoryId = this.selectedNode && this.selectedNode.data.name !== 'Root' ? this.selectedNode.data.id : 1;

    const requestPayload = {
      name: directoryName,
      parent: {
        id: parentDirectoryId
      }
    };

    this.http.post(`${this.apiUrl}/directories/create`, requestPayload, { responseType: 'text'} )
  .subscribe({
      next: () => {
        this.message = 'Directory created successfully';
        this.newDirectoryName = '';
        this.fetchFilesAndDirectories();
      },
      error: (err: any) => {
        console.error('Error creating directory:', err);
        this.message = 'Failed to create directory';
      }
    });
  }

  getParentPath(path: string): string {
    path = path.endsWith('/') ? path.slice(0, -1) : path;
    const lastSlashIndex = path.lastIndexOf('/');
    if (lastSlashIndex === -1) {
      return "root";
    }
    return path.substring(0, lastSlashIndex);
  }

  onDeleteFile(id: number) {
    this.fileUploadService.deleteFile(id).subscribe({
      next: () => {
        this.message = 'File deleted successfully';
        this.fetchFilesAndDirectories();
      },
      error: (err: any) => {
        console.error('Error deleting file:', err);
        this.message = 'Failed to delete file';
      }
    });
  }

  deleteSelectedFile() {
    if (this.selectedNode && this.selectedNode.data.filetype !== 'directory') {
      this.onDeleteFile(this.selectedNode!.data.id);
      this.fetchFilesAndDirectories();
    } else {
      this.message = 'Please select a file to delete';
    }
  }
}
