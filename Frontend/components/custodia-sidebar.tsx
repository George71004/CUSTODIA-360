'use client'

import { LayoutDashboard, FileText, Mic2, Shield, Settings, Circle } from 'lucide-react'
import { cn } from '@/lib/utils'

interface NavItem {
  icon: React.ReactNode
  label: string
  id: string
}

const navItems: NavItem[] = [
  { icon: <Mic2 size={20} />, label: 'Interrogatorios', id: 'interrogation' },
]

interface CustodiaSidebarProps {
  activeView: string
  onViewChange: (viewId: string) => void
}

export function CustodiaSidebar({ activeView, onViewChange }: CustodiaSidebarProps) {

  return (
    <aside className="w-64 bg-sidebar border-r border-sidebar-border flex flex-col h-screen">
      {/* Logo Section */}
      <div className="p-6 border-b border-sidebar-border">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-lg bg-primary flex items-center justify-center">
            <Shield size={24} className="text-primary-foreground" />
          </div>
          <div className="flex-1">
            <h1 className="text-lg font-bold text-primary">CUSTODIA</h1>
            <p className="text-xs text-sidebar-foreground">360</p>
          </div>
        </div>
      </div>

      {/* Navigation Menu */}
      <nav className="flex-1 px-4 py-6 space-y-2">
        {navItems.map((item) => (
          <button
            key={item.id}
            onClick={() => onViewChange(item.id)}
            className={cn(
              'w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-all text-sm font-medium',
              activeView === item.id
                ? 'bg-primary text-primary-foreground shadow-lg'
                : 'text-sidebar-foreground hover:bg-secondary hover:text-primary'
            )}
          >
            <span className="flex-shrink-0">{item.icon}</span>
            <span>{item.label}</span>
          </button>
        ))}
      </nav>

    </aside>
  )
}
