'use client'

import { ChevronRight, Shield, LogOut, AlertTriangle } from 'lucide-react'

interface CustodiaHeaderProps {
  currentView: string
  currentStep?: number
}

const viewTitles: Record<string, { title: string; role: string }> = {
  'dashboard': { title: 'Interrogatorios', role: 'Investigador' },
  'guard-officer': { title: 'Oficial de Guardia', role: 'Oficial de Guardia' },
  'interrogation': { title: 'Interrogatorios', role: 'Investigador' },
  'audit': { title: 'Auditoría de Cadena de Custodia', role: 'Auditor' },
  'settings': { title: 'Configuración del Sistema', role: 'Administrador' },
}

export function CustodiaHeader({ currentView, currentStep }: CustodiaHeaderProps) {
  const viewInfo = viewTitles[currentView] || viewTitles['dashboard']
  
  return (
    <header className="bg-card border-b border-border px-4 py-3 sm:px-6 sm:py-4">
      <div className="flex items-center justify-between gap-2">
        {/* Breadcrumbs */}
        <div className="flex items-center gap-1 sm:gap-2 min-w-0">
          <span className="text-primary font-bold text-sm sm:text-base truncate">{viewInfo.title}</span>
          {currentStep !== undefined && (
            <div className="hidden sm:flex items-center gap-2">
              <ChevronRight size={14} className="text-muted-foreground shrink-0" />
              <span className="text-sm text-foreground whitespace-nowrap">Paso {currentStep} de 6</span>
            </div>
          )}
          {currentView === 'audit' && (
            <div className="hidden sm:flex items-center gap-2">
              <ChevronRight size={14} className="text-muted-foreground shrink-0" />
              <span className="text-sm text-foreground whitespace-nowrap">Expediente #EXP-2026-8902</span>
            </div>
          )}
        </div>

        {/* User Profile & Actions */}
        <div className="flex items-center gap-2 sm:gap-4 shrink-0">
          {/* User Role Badge (Compacto en móvil) */}
          <div className="flex items-center gap-1.5 sm:gap-2 px-2.5 py-1.5 sm:px-4 sm:py-2 bg-secondary rounded-lg border border-border">
            <Shield size={14} className="text-primary" />
            <div className="hidden sm:flex flex-col">
              <span className="text-xs text-muted-foreground leading-tight">Oficial A. Ramírez</span>
              <span className="text-xs font-mono text-primary leading-tight">{viewInfo.role}</span>
            </div>
          </div>

          {/* Emergency Lock Button */}
          <button
            className="p-1.5 sm:p-2 rounded-lg bg-destructive/10 hover:bg-destructive/20 text-destructive border border-destructive/30 transition-all hover:shadow-lg"
            title="Bloqueo de emergencia"
          >
            <AlertTriangle size={16} className="sm:w-[18px] sm:h-[18px]" />
          </button>

          {/* Logout Button */}
          <button
            className="p-1.5 sm:p-2 rounded-lg bg-secondary hover:bg-muted text-foreground border border-border transition-all"
            title="Cerrar sesión"
          >
            <LogOut size={16} className="sm:w-[18px] sm:h-[18px]" />
          </button>
        </div>
      </div>
    </header>
  )
}
